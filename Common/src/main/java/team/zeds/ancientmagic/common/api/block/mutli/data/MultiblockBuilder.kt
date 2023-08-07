package team.zeds.ancientmagic.common.api.block.mutli.data

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block

class MultiblockBuilder {
    private lateinit var config: MultiblockConfig
    private val layers = ArrayList<MultiblockLayer.() -> Unit>()
    private val structure = HashMap<BlockPos, Block>()
    private var yIndex = 0

    fun build(): MultiBlockStructure {
        for (layer in layers.reversed()) {
            val layerImpl = MultiblockLayer(yIndex)
            layer.invoke(layerImpl)
            structure.putAll(layerImpl.layer)

            yIndex++
        }
        MultiBlockStorage.multiBlockActions[config.modelName] = config.onOpen
        return MultiBlockStructure(structure, config)
    }

    fun layer(function: MultiblockLayer.() -> Unit): MultiblockBuilder {
        this.layers.add(function)
        return this
    }

    fun configure(config: MultiblockConfig.()-> Unit): MultiblockBuilder {
        this.config = MultiblockConfig().apply(config)
        return this
    }
}