package api.ancientmagic.atomic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class AtomicUse<T> {
    private final Player player;
    private final Level level;
    private final InteractionHand hand;
    private final BlockHitResult hitResult;
    private final ItemStack stack;
    private final UseOnContext context;
    private final BlockPos pos;
    private final BlockState state;
    private InteractionResultHolder<T> resultHolder;
    private InteractionResult result;

    public AtomicUse(@NotNull Player player, @NotNull Level level, @NotNull InteractionHand hand, ItemStack stack,
                     BlockHitResult result, BlockPos pos, BlockState state) {
        this.player = player;
        this.level = level;
        this.hand = hand;
        this.hitResult = result;
        this.stack = stack;
        this.context = new UseOnContext(level, player, hand, stack, result);
        this.pos = pos;
        this.state = state;
    }

    public AtomicUse(UseOnContext context) {
        this.context = context;
        this.player = context.getPlayer();
        this.level = context.getLevel();
        this.hand = context.getHand();
        this.hitResult = context.getHitResult();
        this.stack = context.getItemInHand();
        this.pos = context.getClickedPos();
        this.state = context.getLevel().getBlockState(this.pos);
    }

    public AtomicUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        this(player, level, hand, null, hitResult, pos, state);
    }

    public AtomicUse(Player player, Level level, InteractionHand hand) {
        this(player, level, hand, player.getItemInHand(hand), null, null, null);
    }

    public AtomicUse() {
        this(null, null, null, null, null, null);
    }

    public InteractionHand getHand() {
        return this.hand;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Level getLevel() {
        return this.level;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public BlockHitResult getHitResult() {
        return this.hitResult;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public UseOnContext getContext() {
        return this.context;
    }

    public BlockState getState() {
        return this.state;
    }

    @NotNull
    public InteractionResultHolder<T> getReturnHolder() {
        return this.resultHolder;
    }

    public void setReturnHolder(InteractionResultHolder<T> resultHolder) {
        this.resultHolder = resultHolder;
    }

    public void setSuccessHolder(T obj) {
        this.setReturnHolder(InteractionResultHolder.success(obj));
    }

    public void setConsumeHolder(T obj) {
        this.setReturnHolder(InteractionResultHolder.consume(obj));
    }

    public void setFailHolder(T obj) {
        this.setReturnHolder(InteractionResultHolder.fail(obj));
    }

    public void setPassHolder(T obj) {
        this.setReturnHolder(InteractionResultHolder.pass(obj));
    }

    public InteractionResult getReturnResult() {
        return this.result;
    }

    public void setReturnResult(InteractionResult result) {
        this.result = result;
    }

    public void setSuccessResult() {
        this.setReturnResult(InteractionResult.SUCCESS);
    }

    public void setConsumeResult() {
        this.setReturnResult(InteractionResult.CONSUME);
    }

    public void setFailResult() {
        this.setReturnResult(InteractionResult.FAIL);
    }

    public void setPassResult() {
        this.setReturnResult(InteractionResult.PASS);
    }
}
