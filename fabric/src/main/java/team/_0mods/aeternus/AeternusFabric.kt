/*
 * All Rights Received
 * Copyright (c) 2024 0mods.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@file:JvmName("AeternusFabric")

package team._0mods.aeternus

import team._0mods.aeternus.platformredirect.common.LOGGER
import team._0mods.aeternus.platformredirect.common.clientInit
import team._0mods.aeternus.platformredirect.common.commonInit

fun startCommon() {
    LOGGER.info("Hello Minecraft world from Common side!")
    commonInit()
}

fun startClient() {
    LOGGER.info("Hello Minecraft world from Client side!")
    clientInit()
}
