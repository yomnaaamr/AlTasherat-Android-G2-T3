package com.mahmoud.altasherat.common.domain.local

import kotlinx.coroutines.flow.Flow
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
    suspend fun <Model : Any> getFlow(
        key: IStorageKeyEnum,
        defaultValue: Model,
        type: KClass<Model>
    ): Flow<Model>

    suspend fun clear()
}