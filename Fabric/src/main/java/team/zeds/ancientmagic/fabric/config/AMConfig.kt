package team.zeds.ancientmagic.fabric.config

import me.shedaniel.autoconfig.*
import me.shedaniel.autoconfig.annotation.*
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Jankson;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.JsonPrimitive
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.api.DeserializationException
import net.minecraft.ResourceLocationException
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.Range
import team.zeds.ancientmagic.common.AMConstant
import team.zeds.ancientmagic.common.init.config.AMConfig

@Config(name = "${AMConstant.MOD_NAME}Common")
class AMCommon: ConfigData, AMConfig.Common {
    @Comment("Mod Compact")
    val modCompact = ModCompact()
    @Comment("Mod Data")
    val modData = ModData()

    class ModCompact {
        @Comment("Mod Compact of Waystones mod")
        val wayStoneCompact = Waystones()

        class Waystones {
            @Comment("Enables Waystone mod compact")
            var isWayStoneCompact = true
            @Comment("Count of consuming Magical Dust on teleport")
            var consumeDust = 2.coerceAtLeast(1).coerceAtMost(64)
        }
    }

    class ModData {
        @Comment("Valid identifiers for commands")
        var commandID = mutableListOf("am, magicancient, ancient, ancientmagic")
        @Comment(
            """
            Enables easy mod
            The hardness functions are disabled
            Good for beginner
            WARNING! THIS FUNCTION IS EXPERIMENTAL! DON'T TOUCH IF YOU ARE AFRAID OF RISKS!
            """
        )
        var enableEasyMod = false
    }

    companion object {
        @JvmStatic
        fun register(): ConfigHolder<AMCommon> = AutoConfig.register(AMCommon::class.java) { cfg, clazz ->
            val janksonBuilder = Jankson.builder()
            janksonBuilder.registerDeserializer(String::class.java, ResourceLocation::class.java) { string, _ ->
                try {
                    return@registerDeserializer ResourceLocation(string)
                } catch (exception: ResourceLocationException) {
                    throw DeserializationException("Not a valid Resource Location: $string", exception)
                }
            }
            janksonBuilder.registerSerializer(ResourceLocation::class.java) { reloc, _ ->
                return@registerSerializer JsonPrimitive(reloc.toString())
            }
            JanksonConfigSerializer(cfg, clazz, janksonBuilder.build())
        }
    }

    override fun isWayStoneCompact(): Boolean = this.modCompact.wayStoneCompact.isWayStoneCompact

    override fun countOfDustConsuming(): Int = this.modCompact.wayStoneCompact.consumeDust

    override fun isEasyMod(): Boolean = this.modData.enableEasyMod
}

@Config(name = "${AMConstant.MOD_NAME}Client")
class AMClient: ConfigData, AMConfig.Client {
    val general = General()

    class General {
        @Comment("Enables a shader renderer for mod. If disable - using standalone shaders provides minecraft")
        var enableShaders = true
    }

    companion object {
        @JvmStatic
        fun register(): ConfigHolder<AMClient> = AutoConfig.register(AMClient::class.java) { cfg, clazz ->
            val janksonBuilder = Jankson.builder()
            janksonBuilder.registerDeserializer(String::class.java, ResourceLocation::class.java) { string, _ ->
                try {
                    return@registerDeserializer ResourceLocation(string)
                } catch (exception: ResourceLocationException) {
                    throw DeserializationException("Not a valid Resource Location: $string", exception)
                }
            }
            janksonBuilder.registerSerializer(ResourceLocation::class.java) { reloc, _ ->
                return@registerSerializer JsonPrimitive(reloc.toString())
            }
            JanksonConfigSerializer(cfg, clazz, janksonBuilder.build())
        }
    }

    override fun isEnableShaders(): Boolean = this.general.enableShaders
}