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

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

fun <T, V> T?.c(): V = this as V

@JvmName("cast")
fun <T, V> c(orig: T): V = orig as V

fun <A, B> ((A) -> B).memorize(): (A) -> B {
    val cache: MutableMap<A, B> = Object2ObjectOpenHashMap()
    return { cache.getOrPut(it) { this(it) } }
}
