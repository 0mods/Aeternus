package team._0mods.aeternus.init.registry

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import team._0mods.aeternus.init.ModId
import team._0mods.aeternus.init.resloc
import java.util.function.Function

object AeternusBlock {
    private val blocks = mutableMapOf<String, Function<BlockBehaviour.Properties, out Block>>()

    val testBlock = register("test_block", ::Block)

    @JvmStatic
    fun getBlocksForRegistry() = this.blocks

    private fun <T: Block> register(
        id: String,
        obj: (BlockBehaviour.Properties) -> T,
        props: BlockBehaviour.Properties = BlockBehaviour.Properties.of()
    ): T {
        val invokedBlock = obj.invoke(props)

        if (blocks.putIfAbsent(id, obj) != null)
            throw IllegalArgumentException("Some bad news... Duplicated id: ${resloc(ModId, id)}")

        return invokedBlock
    }
}