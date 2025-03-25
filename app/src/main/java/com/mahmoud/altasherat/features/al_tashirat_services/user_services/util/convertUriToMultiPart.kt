package com.mahmoud.altasherat.features.al_tashirat_services.user_services.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun Context.getFileFromUri(uri: Uri): File? {
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("upload", ".jpg", cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return tempFile
}

fun File.toImagePart(paramName: String = "image"): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(paramName, this.name, requestFile)
}
