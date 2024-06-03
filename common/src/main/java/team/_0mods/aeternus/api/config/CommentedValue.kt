/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.config

import kotlinx.serialization.Serializable

@Serializable
data class CommentedValue<T>(
    val comment: List<String>,
    val value: T
) {
    companion object {
        fun <T> Companion.create(value: T, vararg comments: String): CommentedValue<T> {
            val list: MutableList<String> = mutableListOf()
            list.addAll(comments)
            return CommentedValue(list, value)
        }
    }

    operator fun invoke() = value
}