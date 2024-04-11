package team._0mods.multilib.ui.animations

class UIAnimation(

) {
}

enum class AnimationTarget {
    OFFSET_X, OFFSET_Y,
    SCALE_X, SCALE_Y,
    ROTATION,
    COLOR_R, COLOR_G, COLOR_B,
    TRANSPARENCY, CUSTOM;

    companion object
}

enum class AnimationTrigger {
    ON_OPEN, ON_CLOSE, ON_CLICK, ON_HOVER, ON_UNHOVER, LOOP, CUSTOM;
}