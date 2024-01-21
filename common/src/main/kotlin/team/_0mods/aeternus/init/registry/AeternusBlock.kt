package team._0mods.aeternus.init.registry

import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import team._0mods.aeternus.init.toRL
import java.util.function.BiConsumer
import java.util.function.Function

object AeternusBlock {
    internal val blocks = mutableMapOf<String, Block>()

    val testBlock = register("test_block", Block(BlockBehaviour.Properties.of()))

    @JvmStatic fun getBlocksForRegistry() = this.blocks

    fun blockReg(cons: BiConsumer<Block, ResourceLocation>) {
        cons.accept(Block(BlockBehaviour.Properties.of()), "aeternus:mod_test".toRL())
    }
}