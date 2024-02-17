@file:Suppress("UNCHECKED_CAST")
package team._0mods.aeternus.api.event.core

import com.google.common.reflect.AbstractInvocationHandler
import org.jetbrains.annotations.ApiStatus
import team._0mods.aeternus.api.event.base.Event
import team._0mods.aeternus.api.event.forge.ForgeEvent
import team._0mods.aeternus.api.event.forge.ForgeEventCancellable
import team._0mods.aeternus.api.event.result.EventResult
import team._0mods.aeternus.api.event.result.EventResultHolder
import team._0mods.aeternus.service.ServiceProvider
import java.lang.invoke.MethodHandles
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.*
import java.util.function.Consumer
import java.util.function.Function

abstract class EventFactory protected constructor() {
    companion object {
        @JvmStatic fun <T> of(function: Function<List<T>, T>): Event<T> = EventImpl(function)

        @JvmStatic
        @SafeVarargs
        fun <T> createNoResult(vararg getterType: T): Event<T> {
            if (getterType.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $getterType")
            return createNoResult(getterType.javaClass.componentType as Class<T>)
        }

        @JvmStatic
        @Throws(Throwable::class)
        private fun <T, R> invokeMethod(listener: T, method: Method, args: Array<out Any?>): R {
            return MethodHandles.lookup().unreflect(method)
                .bindTo(listener).invokeWithArguments(*args) as R
        }

        @JvmStatic
        fun <T> createNoResult(clazz: Class<T>?): Event<T> = of { listeners ->
            Proxy.newProxyInstance(EventFactory::class.java.classLoader, arrayOf(clazz), object : AbstractInvocationHandler() {
                @Throws(Throwable::class)
                override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
                    for (listener in listeners)
                        invokeMethod<T, Any>(listener, method, args)
                    return null
                }
            }) as T
        }

        @JvmStatic
        @SafeVarargs
        fun <T> createEventResult(vararg typeGetter: T?): Event<T> {
            if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
            return createEventResult(typeGetter.javaClass.componentType as Class<T>)
        }

        @JvmStatic
        fun <T> createEventResult(clazz: Class<T>?): Event<T> = of { listeners ->
            Proxy.newProxyInstance(EventFactory::class.java.classLoader, arrayOf(clazz), object : AbstractInvocationHandler() {
                @Throws(Throwable::class)
                override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
                    for (listener in listeners) {
                        val result = Objects.requireNonNull(invokeMethod<T, EventResult>(listener, method, args))
                        if (result.endFurtherEvaluation) return result
                    }

                    return EventResult.pass()
                }
            }) as T
        }

        @JvmStatic
        @SafeVarargs
        fun <T> createEventResultHolder(vararg typeGetter: T): Event<T> {
            if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
            return createEventResult(typeGetter.javaClass.componentType as Class<T>)
        }

        @JvmStatic
        fun <T> createEventResultHolder(clazz: Class<T>?) = of { listeners ->
            Proxy.newProxyInstance(EventFactory::class.java.classLoader, arrayOf(clazz), object : AbstractInvocationHandler() {
                @Throws(Throwable::class)
                override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
                    for (listener in listeners) {
                        val result = Objects.requireNonNull(invokeMethod<T, EventResultHolder<T>>(listener, method, args))
                        if (result.endFurtherEvaluation) return result
                    }

                    return EventResultHolder.pass<T>()
                }
            }) as T
        }

        private val factory: EventFactory
            get() = ServiceProvider.event.eventFactory

        @JvmStatic
        @SafeVarargs
        fun <T> createConsumerLoop(vararg typeGetter: T): Event<Consumer<T>> {
            if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
            return createConsumerLoop(typeGetter.javaClass.componentType as Class<T>)
        }

        @JvmStatic
        fun <T> createConsumerLoop(clazz: Class<T>?): Event<Consumer<T>> {
            val event = of { listeners ->
                Proxy.newProxyInstance(EventFactory::class.java.classLoader, arrayOf(Consumer::class.java), object : AbstractInvocationHandler() {
                    @Throws(Throwable::class)
                    override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
                        for (listener in listeners) {
                            invokeMethod<Any, Any>(listener, method, args)
                        }
                        return null
                    }

                }) as Consumer<T>
            }

            var superClass: Class<*>? = clazz

            while (superClass != null) {
                if (superClass.isAnnotationPresent(ForgeEvent::class.java))
                    return factory.attachToForge(event)

                superClass = superClass.superclass
            }

            return event
        }

        @JvmStatic
        @SafeVarargs
        fun <T> createEventActorLoop(vararg typeGetter: T): Event<EventActor<T>> {
            if (typeGetter.isNotEmpty()) throw IllegalStateException("Array must be empty! Founded values: $typeGetter")
            return createEventActorLoop(typeGetter.javaClass.componentType as Class<T>)
        }

        @JvmStatic
        fun <T> createEventActorLoop(clazz: Class<T>?): Event<EventActor<T>> {
            val event = of { listeners ->
                Proxy.newProxyInstance(EventFactory::class.java.classLoader, arrayOf(EventActor::class.java), object : AbstractInvocationHandler() {
                    @Throws(Throwable::class)
                    override fun handleInvocation(proxy: Any, method: Method, args: Array<out Any?>): Any? {
                        for (listener in listeners) {
                            invokeMethod<Any, Any>(listener, method, args)
                        }
                        return null
                    }

                }) as EventActor<T>
            }

            var superClass: Class<*>? = clazz

            while (superClass != null) {
                if (superClass.isAnnotationPresent(ForgeEventCancellable::class.java))
                    return factory.attachToForgeEventActorCancellable(event)

                superClass = superClass.superclass
            }

            superClass = clazz

            while (superClass != null) {
                if (superClass.isAnnotationPresent(ForgeEvent::class.java))
                    return factory.attachToForgeEventActor(event)

                superClass = superClass.superclass
            }

            return event
        }
    }

    @ApiStatus.Internal
    abstract fun <T> attachToForge(evt: Event<Consumer<T>>): Event<Consumer<T>>

    @ApiStatus.Internal
    abstract fun <T> attachToForgeEventActor(evt: Event<EventActor<T>>): Event<EventActor<T>>

    @ApiStatus.Internal
    abstract fun <T> attachToForgeEventActorCancellable(evt: Event<EventActor<T>>): Event<EventActor<T>>

    private class EventImpl<T>(private val function: Function<List<T>, T>): Event<T> {
        private var invoker: T? = null
        private val listeners: ArrayList<T> = arrayListOf()

        override val event: T
            get() {
                if (invoker == null) update()

                return invoker!!
            }

        override fun resetListeners() {
            listeners.clear()
            listeners.trimToSize()
            invoker = null
        }

        override fun isRegistered(listener: T): Boolean = listeners.contains(listener)

        override fun unregister(listener: T) {
            listeners.remove(listener)
            listeners.trimToSize()
            invoker = null
        }

        override fun register(listener: T) {
            listeners.add(listener)
            invoker = null
        }

        fun update() {
            invoker = if (listeners.size == 1) listeners[0]
            else function.apply(listeners)
        }
    }
}
