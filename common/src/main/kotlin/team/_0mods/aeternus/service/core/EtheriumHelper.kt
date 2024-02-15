package team._0mods.aeternus.service.core

import net.minecraft.world.entity.player.Player

interface EtheriumHelper {
    fun add(addFor: Player, count: Int)

    fun consume(consumeFrom: Player, count: Int)

    fun getCountForPlayer(countFor: Player): Int
}