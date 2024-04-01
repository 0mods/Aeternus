/*
 * MIT License
 *
 * Copyright (c) 2024. AlgorithmLX & 0mods.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package team._0mods.multilib.event.base.common

import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import team._0mods.multilib.event.core.EventFactory

interface LifecycleEvent {
    companion object {
        @JvmField val SERVER_ABOUT_TO_START = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STARTING = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STARTED = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STOPPING = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_STOPPED = EventFactory.createNoResult<ServerState>()
        @JvmField val SERVER_LEVEL_LOAD = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SERVER_LEVEL_UNLOAD = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SERVER_LEVEL_SAVE = EventFactory.createNoResult<ServerLevelState>()
        @JvmField val SETUP = EventFactory.createNoResult<Runnable>()
    }

    fun interface ServerState: InstanceState<MinecraftServer>

    fun interface ServerLevelState: LevelState<ServerLevel>

    fun interface InstanceState<T> {
        fun onChanged(instance: T)
    }

    fun interface LevelState<T: Level> {
        fun onChanged(instance: T)
    }
}
