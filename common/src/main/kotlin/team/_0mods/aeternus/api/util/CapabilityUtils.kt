/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.util

import team._0mods.aeternus.api.capability.CapabilityInstance
import team._0mods.aeternus.api.client.models.manager.AnimatedEntityCapability
import kotlin.reflect.KClass

val entCap = AnimatedEntityCapability()
operator fun <O, T : CapabilityInstance> O.get(capability: KClass<T>): T = entCap as T // TODO("Fix capabilities")
operator fun <O, T : CapabilityInstance> O.get(capability: Class<T>): T = TODO("Fix capabilities")