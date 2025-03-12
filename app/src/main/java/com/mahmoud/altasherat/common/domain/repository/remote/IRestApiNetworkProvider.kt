package com.mahmoud.altasherat.common.domain.repository.remote

import kotlin.reflect.KClass

interface IRestApiNetworkProvider {

    suspend fun <T : Any> get(
        endpoint: String,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: KClass<T>
    ): T

    suspend fun <T : Any> post(
        endpoint: String,
        body: Any? = null,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: KClass<T>
    ): T

    suspend fun <T : Any> put(
        endpoint: String,
        body: Any? = null,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: KClass<T>
    ): T

    suspend fun <T : Any> delete(
        endpoint: String,
        body: Any? = null,
        headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null,
        responseType: KClass<T>
    ): T
}