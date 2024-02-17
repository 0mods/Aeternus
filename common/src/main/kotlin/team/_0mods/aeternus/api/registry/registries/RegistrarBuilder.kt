/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.registry.registries

import team._0mods.aeternus.api.registry.registries.option.RegistrarOption
import team._0mods.aeternus.api.registry.registries.option.StandardRegistrarOption

interface RegistrarBuilder<T> {
    fun build(): Registrar<T>

    fun option(option: RegistrarOption): RegistrarBuilder<T>

    fun syncToClients(): RegistrarBuilder<T> = option(StandardRegistrarOption.SYNC_TO_CLIENTS)
}
