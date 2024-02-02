package team._0mods.aeternus.forge.api

import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.common.util.NonNullSupplier

fun <T> lazyOptOf(sup: NonNullSupplier<T>) = LazyOptional.of(sup)

fun <T> emptyLazyOpt() = LazyOptional.empty<T>()