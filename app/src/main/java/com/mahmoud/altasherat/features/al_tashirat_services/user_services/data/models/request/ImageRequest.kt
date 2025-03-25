package com.mahmoud.altasherat.features.al_tashirat_services.user_services.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import java.io.File

class ImageRequest(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("type") val type: String = "",
    @SerializedName("path") val imageFile: File? = null,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String? = null,
    @SerializedName("priority") val priority: Int = 0,
    @SerializedName("main") val main: Boolean = false,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
) {
    fun validateImageRequest(): Resource<MutableList<ValidationError>> {
        val errors = mutableListOf<ValidationError>()
        listOf(
            validateImageExtension(),
            validateImageSize()
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }

        return Resource.Success(errors)
    }

    private fun validateImageExtension(): Resource<Unit> {
        val allowedExtensions = listOf("jpg", "jpeg", "png")
        val extension = imageFile?.extension?.lowercase()

        if (extension !in allowedExtensions) {
            return Resource.Error(ValidationError.INVALID_IMAGE_EXTENSION)
        }
        return Resource.Success(Unit)
    }

    private fun validateImageSize(): Resource<Unit> {
        val maxSize = 512 * 1024 // 512 KB
        if (imageFile?.length()!! > maxSize) {
            return Resource.Error(ValidationError.INVALID_IMAGE_SIZE)
        }
        return Resource.Success(Unit)
    }
}