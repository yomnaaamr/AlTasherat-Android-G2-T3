package com.mahmoud.altasherat.common.data.repository.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mahmoud.altasherat.common.domain.repository.local.ILocalStorageProvider
import com.mahmoud.altasherat.common.domain.repository.local.IStorageKeyEnum
import com.mahmoud.altasherat.common.domain.util.error.LocalStorageError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.reflect.KClass

private val Context.dataStore by preferencesDataStore(name = "Altasherat_preferences")

class LocalStorageProvider @Inject constructor(
    private val context: Context,
) : ILocalStorageProvider {


    override suspend fun <Model : Any> save(
        key: IStorageKeyEnum,
        value: Model,
        type: KClass<Model>,
    ) {
        val preferencesKey = preferencesKey(key, type)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun <Model : Any> get(
        key: IStorageKeyEnum,
        defaultValue: Model,
        type: KClass<Model>,
    ): Model {
        val preferencesKey = preferencesKey(key, type)
        return context.dataStore.data
            .map { preferences -> preferences[preferencesKey] ?: defaultValue }
            .first()
    }

    override suspend fun <Model : Any> update(
        key: IStorageKeyEnum,
        value: Model,
        type: KClass<Model>,
    ) {
        save(key, value, type)
    }

    override suspend fun <Model : Any> delete(key: IStorageKeyEnum, type: KClass<Model>) {
        val preferencesKey = preferencesKey(key, type)
        context.dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }


    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }


    @Suppress("UNCHECKED_CAST")
    private fun <Model : Any> preferencesKey(
        key: IStorageKeyEnum,
        type: KClass<Model>,
    ): Preferences.Key<Model> {
        return when (type) {
            Boolean::class -> booleanPreferencesKey(key.keyValue)
            Float::class -> floatPreferencesKey(key.keyValue)
            Int::class -> intPreferencesKey(key.keyValue)
            Long::class -> longPreferencesKey(key.keyValue)
            String::class -> stringPreferencesKey(key.keyValue)
            Set::class -> stringSetPreferencesKey(key.keyValue)
            else -> throw AltasheratException(LocalStorageError.TYPE_MISMATCH)
        } as Preferences.Key<Model>
    }

}





