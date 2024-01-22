package team._0mods.aeternus.init.registry

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import java.util.function.Function

object AeternusBlocks {
    internal val blocks = mutableMapOf<String, Function<BlockBehaviour.Properties, out Block>>()

    val testBlock = registerBlock("test_block", ::Block)

    @JvmStatic fun getBlocksForRegistry() = this.blocks
}