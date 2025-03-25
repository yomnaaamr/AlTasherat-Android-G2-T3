package com.mahmoud.altasherat.common.data.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun getFileFromUri(context: Context, uri: Uri?): File? {
    if (uri == null) return null
    val contentResolver = context.contentResolver
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)

    contentResolver.openInputStream(uri)?.use { inputStream ->
        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
    }
    return tempFile
}

fun createImagePart(context: Context, uri: Uri?): MultipartBody.Part? {
    val file = getFileFromUri(context, uri) ?: return null
    val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("image", file.name, requestFile)
}