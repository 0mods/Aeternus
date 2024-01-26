package team._0mods.aeternus.api.gui.widget

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource.BufferSource

abstract class BookWidget(
    @Expose
    @SerializedName("page")
    val displayPage: Int,
    @Expose
    val type: Type,
    @Expose
    val x: Int,
    @Expose
    val y: Int,
    @Expose
    val scale: Float
) {
    abstract fun render(pose: PoseStack, buffer: BufferSource, partialTick: Float, onFlip: Boolean)

    enum class Type(val widget: Class<out BookWidget>)
}
