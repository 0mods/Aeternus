package team._0mods.aeternus.platformredirect.api.block.magic

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import team._0mods.aeternus.api.magic.block.CursedBlockCondition
import team._0mods.aeternus.api.magic.research.Research
import team._0mods.aeternus.service.ResearchHelper

fun LivingEntity.equalsEffect(effect: MobEffect): CursedBlockCondition = EqualEffect(effect, this)

fun Player.equalsResearch(research: Research): CursedBlockCondition = EqualResearch(this, research)

class EqualEffect internal constructor(private val effect: MobEffect, private val livingEntity: LivingEntity): CursedBlockCondition {
    override fun isSuccess(): Boolean = livingEntity.activeEffects.equals(effect)
}

class EqualResearch internal constructor(private val player: Player, private val research: Research): CursedBlockCondition {
    override fun isSuccess(): Boolean = ResearchHelper.hasResearch(player, research)
}
