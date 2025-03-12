package com.mahmoud.altasherat.common.data.repository.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap


@JvmSuppressWildcards
interface RetrofitApiService {

    @GET("{path}")
    suspend fun get(
        @Path("path", encoded = true) endpoint: String,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>


    @POST("{path}")
    suspend fun post(
        @Path("path", encoded = true) endpoint: String,
        @Body body: Any,
        @HeaderMap headers: Map<String, Any>,
        @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>

    @PUT("{path}")
    suspend fun put(
        @Path("path", encoded = true) endpoint: String,
        @Body body: Any,
        @QueryMap queryParams: Map<String, Any>,
        @HeaderMap headers: Map<String, Any>
    ): Response<ResponseBody>

    @DELETE("{path}")
    suspend fun delete(
        @Path("path", encoded = true) endpoint: String,
        @QueryMap queryParams: Map<String, Any>,
        @Body body: Any,
        @HeaderMap headers: Map<String, Any>
    ): Response<ResponseBody>
}