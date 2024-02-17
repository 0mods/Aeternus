/*
 * All Rights Received
 * Copyright (c) 2024 AlgorithmLX & 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package team._0mods.aeternus.api.event.core

import team._0mods.aeternus.service.ServiceProvider

abstract class EventHandler protected constructor() {
    companion object {
        private var isInitialized = false

        fun init() {
            if (isInitialized) return
            isInitialized = true

            val platform = ServiceProvider.platform
            val handlerImpl = ServiceProvider.event.eventHandler

            if (platform.isPhysicalClient()) handlerImpl.registerClient()
            handlerImpl.registerCommon()
            if (platform.isPhysicalServer()) handlerImpl.registerServer()
        }
    }

    abstract fun registerClient()

    abstract fun registerCommon()

    abstract fun registerServer()
}
