package team.zeromods.ancientmagic.api.atomic

import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class KAtomicUse<T> {
    val player: Player
    val level: Level
    val hand: InteractionHand
    val hitResult: BlockHitResult
    val stack: ItemStack
    val context: UseOnContext
    val pos: BlockPos
    val state: BlockState
    var returnHolder: InteractionResultHolder<T>? = null
    var returnResult: InteractionResult? = null

    @JvmOverloads
    constructor(
        player: Player?, level: Level?, hand: InteractionHand?, stack: ItemStack? = hand?.let {
            player!!.getItemInHand(it)
        },
        result: BlockHitResult? = null, pos: BlockPos? = null, state: BlockState? = null
    ) {
        this.player = player!!
        this.level = level!!
        this.hand = hand!!
        hitResult = result!!
        this.stack = stack!!
        context = UseOnContext(level, player, hand, stack, result)
        this.pos = pos!!
        this.state = state!!
    }

    constructor(context: UseOnContext) {
        this.context = context
        player = context.player!!
        level = context.level
        hand = context.hand
        hitResult = context.hitResult
        stack = context.itemInHand
        pos = context.clickedPos
        state = context.level.getBlockState(pos)
    }

    @JvmOverloads
    constructor(
        state: BlockState? = null,
        level: Level? = null,
        pos: BlockPos? = null,
        player: Player? = null,
        hand: InteractionHand? = null,
        hitResult: BlockHitResult? = null
    ) : this(player, level, hand, null, hitResult, pos, state)

    fun setConsume(obj: T) {
        this.returnHolder = InteractionResultHolder.consume(obj)
    }

    fun setSuccess(obj: T) {
        this.returnHolder = InteractionResultHolder.success(obj)
    }

    fun setFail(obj: T) {
        this.returnHolder = InteractionResultHolder.pass(obj)
    }

    fun setPass(obj: T) {
        this.returnHolder = InteractionResultHolder.pass(obj)
    }

    fun setSidedSuccess(obj: T, canSuccess: Boolean) {
        this.returnHolder = InteractionResultHolder.sidedSuccess(obj, canSuccess)
    }
}

