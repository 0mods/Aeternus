package team.zeds.ancientmagic.common.api.registry

import team.zeds.ancientmagic.common.api.block.mutli.data.MultiBlockStructure
import team.zeds.ancientmagic.common.api.block.mutli.data.MultiblockBuilder

interface IAMMultiblocks {
    fun init()

    companion object {
        val storage = mutableListOf<MultiBlockStructure>()

        fun registryMultiblock(builder: MultiblockBuilder) {
            storage.add(builder.build())
        }
    }
}