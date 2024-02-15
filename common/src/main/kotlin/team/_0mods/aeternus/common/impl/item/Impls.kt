package team._0mods.aeternus.common.impl.item

import net.minecraft.world.item.*

internal class PickaxeItemImpl(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    properties: Properties
): PickaxeItem(tier, attackDamage.toInt(), attackSpeed, properties)

internal class SwordItemImpl(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    properties: Properties
): SwordItem(tier, attackDamage.toInt(), attackSpeed, properties)

internal class AxeItemImpl(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    properties: Properties
): AxeItem(tier, attackDamage, attackSpeed, properties)

internal class ShovelItemImpl(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    properties: Properties
): ShovelItem(tier, attackDamage, attackSpeed, properties)

internal class HoeItemImpl(
    tier: Tier,
    attackDamage: Float,
    attackSpeed: Float,
    properties: Properties
): HoeItem(tier, attackDamage.toInt(), attackSpeed, properties)
