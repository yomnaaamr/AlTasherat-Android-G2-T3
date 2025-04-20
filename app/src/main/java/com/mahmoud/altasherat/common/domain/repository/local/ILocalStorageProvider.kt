package com.mahmoud.altasherat.common.domain.repository.local

import kotlin.reflect.KClass

interface ILocalStorageProvider {
    suspend fun <Model : Any> save(key: IStorageKeyEnum, value: Model, type: KClass<Model>)
    suspend fun <Model : Any> get(
        key: IStorageKeyEnum,
        defaultValue: Model,
        type: KClass<Model>
    ): Model

    suspend fun <Model : Any> update(key: IStorageKeyEnum, value: Model, type: KClass<Model>)
    suspend fun <Model : Any> delete(key: IStorageKeyEnum, type: KClass<Model>)
    suspend fun clear()
    suspend fun<Model: Any> contains(key: IStorageKeyEnum,type: KClass<Model>): Boolean
}