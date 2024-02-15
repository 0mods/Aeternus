package team._0mods.aeternus.api.registry.registries.impl

import com.mojang.datafixers.util.Either
import net.minecraft.core.Holder
import net.minecraft.core.HolderOwner
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey
import team._0mods.aeternus.api.registry.registries.RegistrySupplier
import java.util.*
import java.util.function.Predicate
import java.util.stream.Stream

interface RegistrySupplierImpl<T>: RegistrySupplier<T> {
    val holder: Holder<T>?

    override fun value(): T = get()

    override fun isBound(): Boolean = isPresent

    override fun `is`(p0: ResourceLocation): Boolean = id == p0

    override fun `is`(p0: ResourceKey<T>): Boolean = key == p0

    override fun `is`(p0: Predicate<ResourceKey<T>>): Boolean = p0.test(key)

    override fun `is`(p0: TagKey<T>): Boolean = holder != null && holder!!.`is`(p0)

    override fun tags(): Stream<TagKey<T>> = if (holder != null) holder!!.tags() else Stream.empty()

    override fun unwrap(): Either<ResourceKey<T>, T> = Either.left(key)

    override fun unwrapKey(): Optional<ResourceKey<T>> = Optional.of(key)

    override fun kind(): Holder.Kind = Holder.Kind.REFERENCE

    override fun canSerializeIn(p0: HolderOwner<T>): Boolean = holder != null && holder!!.canSerializeIn(p0)
}