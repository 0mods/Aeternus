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

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BaseSpawner
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import team._0mods.multilib.event.core.EventFactory
import team._0mods.multilib.event.result.EventResult

interface EntityEvent {
    companion object {
        @JvmField val LIVING_DEATH = EventFactory.createEventResult<LivingDeath>()
        @JvmField val HURT = EventFactory.createEventResult<LivingHurt>()
        @JvmField val CHECK_SPAWN = EventFactory.createEventResult<LivingCheckSpawn>()
        @JvmField val JOIN_WORLD = EventFactory.createEventResult<JoinWorld>()
        @JvmField val ENTER_SECTION = EventFactory.createNoResult<EnterSection>()
        @JvmField val ANIMAL_TAME = EventFactory.createEventResult<AnimalTame>()
    }

    fun interface LivingDeath {
        fun die(entity: LivingEntity, source: DamageSource): EventResult
    }

    fun interface LivingHurt {
        fun hurt(entity: LivingEntity, source: DamageSource, amount: Float): EventResult
    }

    fun interface LivingCheckSpawn {
        fun canSpawn(entity: LivingEntity, level: LevelAccessor, x: Double, y: Double, z: Double, type: MobSpawnType, spawner: BaseSpawner?): EventResult
    }

    fun interface JoinWorld {
        fun join(entity: Entity, level: Level): EventResult
    }

    fun interface EnterSection {
        fun enterSection(entity: Entity, sectionX: Int, sectionY: Int, sectionZ: Int, prevX: Int, prevY: Int, prevZ: Int)
    }

    fun interface AnimalTame {
        fun tame(animal: Animal, player: Player): EventResult
    }
}
