package com.mahmoud.altasherat.common.data.repository.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
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

    @Multipart
    @POST("{path}")
    suspend fun postFiles(
        @Path("path", encoded = true) endpoint: String,
        @Part files: List<MultipartBody.Part>?,
        @PartMap data: Map<String, RequestBody>,
        @HeaderMap headers: Map<String, Any>,
    ): Response<ResponseBody>
}