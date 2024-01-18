package team._0mods.aeternus.api.block.blockentity

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.block.entity.BlockEntity

interface IMenued {
    fun getContainer(index: Int, inv: Inventory, player: Player, be: BlockEntity): AbstractContainerMenu?
}