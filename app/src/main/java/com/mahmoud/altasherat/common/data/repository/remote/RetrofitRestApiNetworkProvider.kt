package com.mahmoud.altasherat.common.data.repository.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.mahmoud.altasherat.common.domain.repository.remote.IRestApiNetworkProvider
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.NetworkError
import com.mahmoud.altasherat.common.domain.util.exception.AltasheratException
import kotlinx.coroutines.ensureActive
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext
import kotlin.reflect.KClass


class RetrofitRestApiNetworkProvider(
    private val apiService: RetrofitApiService,
    private val gson: Gson,
) : IRestApiNetworkProvider {


    override suspend fun <T : Any> get(
        endpoint: String,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: KClass<T>,
    ): T = safeApiCall(responseType) {
        apiService.get(
            endpoint = endpoint,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
    }

    override suspend fun <T : Any> post(
        endpoint: String,
        body: Any?,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: KClass<T>,
    ): T = safeApiCall(responseType) {
        apiService.post(
            endpoint = endpoint,
            body = body ?: Unit,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
    }

    override suspend fun <T : Any> put(
        endpoint: String,
        body: Any?,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: KClass<T>,
    ): T = safeApiCall(responseType) {
        apiService.put(
            endpoint = endpoint,
            body = body ?: Unit,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
    }

    override suspend fun <T : Any> delete(
        endpoint: String,
        body: Any?,
        headers: Map<String, Any>?,
        queryParams: Map<String, Any>?,
        responseType: KClass<T>,
    ): T = safeApiCall(responseType) {
        apiService.delete(
            endpoint = endpoint,
            body = body ?: Unit,
            headers = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
    }

    override suspend fun <T : Any> updateAccount(
        endpoint: String,
        image: MultipartBody.Part?,
        data: Map<String, RequestBody>,
        headers: Map<String, Any>?,
        responseType: KClass<T>
    ): T = safeApiCall(responseType) {
        apiService.updateAccount(
            endpoint = endpoint,
            image = image,
            data = data,
            headers = headers ?: hashMapOf(),
        )
    }
    
    private suspend inline fun <T : Any> safeApiCall(
        responseType: KClass<T>,
        execute: () -> Response<ResponseBody>,
    ): T {
        val response = try {
            execute()
        } catch (e: UnknownHostException) {
            throw AltasheratException(NetworkError.NO_INTERNET)
        } catch (e: UnresolvedAddressException) {
            throw AltasheratException(NetworkError.NO_INTERNET)
        } catch (e: JsonParseException) {
            throw AltasheratException(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            throw AltasheratException(AltasheratError.UnknownError(e.toString()))
        }

        return handleResponse(response, responseType)
    }

    private fun <T : Any> handleResponse(
        response: Response<ResponseBody>,
        responseType: KClass<T>,
    ): T {
        return if (response.isSuccessful) {
            val responseString =
                response.body() ?: throw AltasheratException(
                    AltasheratError.UnknownServerError(
                        response.code()
                    )
                )
            gson.fromJson(responseString.string(), responseType.java)

        } else {
            Log.d("AITASHERAT", "error code = ${response.code()}")
            when (response.code()) {
                400 -> throw AltasheratException(NetworkError.BAD_REQUEST)
                401 -> throw AltasheratException(NetworkError.UNAUTHORIZED)
                403 -> throw AltasheratException(NetworkError.FORBIDDEN)
                404 -> throw AltasheratException(NetworkError.NOT_FOUND)
                422 -> throw AltasheratException(NetworkError.InvalidCredentials)
                else -> throw AltasheratException(AltasheratError.UnknownServerError(response.code()))
            }
        }
    }


}