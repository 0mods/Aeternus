/*
 * All Rights Received
 * Copyright (c) 2024.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.magic.research.book

enum class ResearchShape {
    SQUARE,
    CIRCLE;

    companion object {
        fun getById(id: Int): ResearchShape {
            val values = entries.toTypedArray().size - 1
            if (id > values) throw IllegalStateException("Research shape id '$id' is larger than $values")

            return ResearchShape.entries[id]
        }
    }
}
