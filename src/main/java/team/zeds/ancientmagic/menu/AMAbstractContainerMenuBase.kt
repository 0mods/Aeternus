package team.zeds.ancientmagic.menu

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.wrapper.InvWrapper

@Suppress("NAME_SHADOWING")
abstract class AMAbstractContainerMenuBase(p_38851_: MenuType<*>?, p_38852_: Int, inv: Inventory)
    : AbstractContainerMenu(p_38851_, p_38852_) {
    private val playerInventory: IItemHandler

    init {
        this.playerInventory = InvWrapper(inv)
    }

    open fun addSlotRange(handler: IItemHandler, index: Int, x: Int, y: Int, amount: Int, dx: Int): Int {
        var index = index
        var x = x
        for (i in 0 until amount) {
            addSlot(SlotItemHandler(handler, index, x, y))
            x += dx
            index++
        }
        return index
    }

    open fun addSlotBox(
        handler: IItemHandler,
        index: Int,
        x: Int,
        y: Int,
        horAmount: Int,
        dx: Int,
        verAmount: Int,
        dy: Int,
    ) {
        var index = index
        var y = y
        for (j in 0 until verAmount) {
            index = addSlotRange(handler, index, x, y, horAmount, dx)
            y += dy
        }
    }

    open fun makeInventorySlots(leftColX: Int, topRowY: Int) {
        var topRowY = topRowY
        addSlotBox(playerInventory, 9, leftColX, topRowY, 9, 18, 3, 18)
        topRowY += 58
        addSlotRange(playerInventory, 0, leftColX, topRowY, 9, 18)
    }
}