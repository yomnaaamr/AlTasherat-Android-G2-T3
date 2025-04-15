package com.mahmoud.altasherat.features.al_tashirat_services.user.util

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun Uri.toFile(context: Context): File? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(this) ?: return null

    // Get MIME type
    val mimeType = contentResolver.getType(this) ?: return null

    // Map common MIME types to extensions
    val extension = when (mimeType) {
        "image/jpeg" -> ".jpg"
        "image/png" -> ".png"
        "application/pdf" -> ".pdf"
        else -> return null // Unsupported file type
    }

    // Create temp file with correct extension
    val tempFile = File.createTempFile("upload", extension, context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return tempFile
}


fun File.toImagePart(paramName: String = "image"): MultipartBody.Part {
    val requestFile = this.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(paramName, this.name, requestFile)
}

fun List<File>.toPassportImageParts(): List<MultipartBody.Part> {
    return this.mapIndexed { index, file ->
        val key = "passport_images[$index]"
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(key, file.name, requestFile)
    }
}

fun List<File>.toAttachmentsParts(): List<MultipartBody.Part> {
    return this.mapIndexed { index, file ->
        val key = "attachments[$index]"
        val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(key, file.name, requestFile)
    }
}

//fun File.toAttachmentsPart(paramName: String = "attachments[]"): MultipartBody.Part {
//    val requestFile = this.asRequestBody("attachments/*".toMediaTypeOrNull())
//    return MultipartBody.Part.createFormData(paramName, this.name, requestFile)
//}
