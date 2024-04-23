/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
