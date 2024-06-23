package team._0mods.aeternus.platformredirect.api.magic.spell

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
interface ItemSpell {
    val properties: Item.Properties
        get() = Item.Properties()

    fun interact(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> =
        InteractionResultHolder.pass(player.getItemInHand(usedHand))

    fun interactOn(ctx: UseOnContext): InteractionResult
}