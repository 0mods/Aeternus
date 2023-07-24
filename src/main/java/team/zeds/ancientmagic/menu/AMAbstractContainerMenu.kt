package team.zeds.ancientmagic.menu

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import net.minecraftforge.items.wrapper.InvWrapper

abstract class AMAbstractContainerMenu(menuType: MenuType<*>?, p_38852_: Int, inv: Inventory)
    : AbstractContainerMenu(menuType, p_38852_) {
    private val playerInventory: IItemHandler

    init {
        this.playerInventory = InvWrapper(inv)
    }

    open fun addSlotRange(handler: IItemHandler, index: Int, x: Int, y: Int, amount: Int, dx: Int): Int {
        var i0 = index
        var x0 = x
        for (i in 0 until amount) {
            addSlot(SlotItemHandler(handler, i0, x0, y))
            x0 += dx
            i0++
        }
        return i0
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
        var i = index
        var y0 = y
        for (j in 0 until verAmount) {
            i = addSlotRange(handler, i, x, y0, horAmount, dx)
            y0 += dy
        }
    }

    open fun makeInventorySlots(leftColX: Int, topRowY: Int) {
        var tRY = topRowY
        addSlotBox(playerInventory, 9, leftColX, tRY, 9, 18, 3, 18)
        tRY += 58
        addSlotRange(playerInventory, 0, leftColX, tRY, 9, 18)
    }
}