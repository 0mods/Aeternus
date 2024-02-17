/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.util

import com.google.common.base.Supplier
import java.util.function.Consumer
import java.util.function.IntConsumer
import java.util.function.IntSupplier

fun interface FloatSupplier {
    fun getAsFloat(): Float
}

interface Value<T>: Supplier<T>, Consumer<T>

interface BaseNumberValue<T: Number>: Value<T> {
    var asT: T
}

interface IntValue: BaseNumberValue<Int>, IntSupplier, IntConsumer {
    override fun getAsInt(): Int = this.asT

    override fun accept(p0: Int) {
        this.asT = p0
    }
}
