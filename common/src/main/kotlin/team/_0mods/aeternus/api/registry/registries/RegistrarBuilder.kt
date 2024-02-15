package team._0mods.aeternus.api.registry.registries

import team._0mods.aeternus.api.registry.registries.option.RegistrarOption
import team._0mods.aeternus.api.registry.registries.option.StandardRegistrarOption

interface RegistrarBuilder<T> {
    fun build(): Registrar<T>

    fun option(option: RegistrarOption): RegistrarBuilder<T>

    fun syncToClients(): RegistrarBuilder<T> = option(StandardRegistrarOption.SYNC_TO_CLIENTS)
}