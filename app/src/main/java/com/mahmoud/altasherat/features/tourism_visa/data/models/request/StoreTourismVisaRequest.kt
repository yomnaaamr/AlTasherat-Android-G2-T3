package com.mahmoud.altasherat.features.tourism_visa.data.models.request

import com.google.gson.annotations.SerializedName
import com.mahmoud.altasherat.common.domain.util.Resource
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.util.Constants.EMAIL_PATTERN
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.regex.Pattern

data class StoreTourismVisaRequest(
    @SerializedName("firstname")
    val firstName: String,
    @SerializedName("middlename")
    val middleName: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("gender")
    val gender: Boolean,
    @SerializedName("birthdate")
    val birthDate: String,
    @SerializedName("passport_number")
    val passportNumber: String,
    @SerializedName("passport_images")
    val passportImages: List<File>,
    @SerializedName("attachments")
    val attachments: List<File>,
    @SerializedName("phone[country_code]")
    val countryCode: String,
    @SerializedName("phone[number]")
    val number: String,
    @SerializedName("contact_email")
    val email: String,
    @SerializedName("destination_country")
    val destinationCountry: String,
    @SerializedName("purpose_of_visit")
    val purposeOfVisit: String,
    @SerializedName("adults_count")
    val adultsCount: Int,
    @SerializedName("children_count")
    val childrenCount: Int,
    @SerializedName("message")
    val message: String?,

    ) {

    fun validateRequest(): Resource<MutableList<ValidationError>> {
        val errors = mutableListOf<ValidationError>()
        listOf(
            validateFirstName(),
            validateMiddleName(),
            validateLastName(),
            validateBirthDate(),
            validatePassportNumber(),
            validatePassportImages(),
            validateAttachments(),
            validateEmail(),
            validatePhoneNumber(),
            validatePhoneCountryCode(),
            validateDestinationCountry(),
            validatePurposeOfVisit(),
            validateAdultsCount(),
            validateChildrenCount(),
            validateMessage()
        ).forEach { result ->
            if (result is Resource.Error) {
                errors.add(result.error as ValidationError)
            }
        }
        return Resource.Success(errors)
    }

    private fun validateFirstName(): Resource<Unit> {
        if (firstName.isBlank()) return Resource.Error(ValidationError.EMPTY_FIRSTNAME)
        if (firstName.length < 3 || firstName.length > 10) return Resource.Error(ValidationError.INVALID_FIRSTNAME)
        return Resource.Success(Unit)
    }

    private fun validateMiddleName(): Resource<Unit> {
        if (middleName.isBlank()) return Resource.Error(ValidationError.EMPTY_MIDDLE_NAME)
        if (middleName.length < 3 || middleName.length > 10) return Resource.Error(ValidationError.INVALID_MIDDLE_NAME)
        return Resource.Success(Unit)
    }

    private fun validateLastName(): Resource<Unit> {
        if (lastname.isBlank()) return Resource.Error(ValidationError.EMPTY_LASTNAME)
        if (lastname.length < 3 || lastname.length > 10) return Resource.Error(ValidationError.INVALID_LASTNAME)
        return Resource.Success(Unit)
    }

    private fun validateBirthDate(): Resource<Unit> {
        if (birthDate.isBlank()) return Resource.Error(ValidationError.EMPTY_BIRTHDATE)
        return Resource.Success(Unit)
    }

    private fun validatePassportNumber(): Resource<Unit> {
        if (passportNumber.isBlank()) return Resource.Error(ValidationError.EMPTY_PASSPORT_NUMBER)
        if (passportNumber.length < 14 || passportNumber.length > 20) return Resource.Error(
            ValidationError.INVALID_PASSPORT_NUMBER
        )
        return Resource.Success(Unit)
    }

    private fun validatePassportImages(): Resource<Unit> {
        if (passportImages.isEmpty()) return Resource.Error(ValidationError.EMPTY_PASSPORT_IMAGES)
        if (passportImages.size > 4) return Resource.Error(ValidationError.INVALID_PASSPORT_IMAGES)
        val allowedExtensions = listOf("jpg", "jpeg", "png")
        val maxSize = 512 * 1024 // 512 KB

        passportImages.forEach { image ->
            val extension = image.extension.lowercase()
            if (extension !in allowedExtensions) {
                return Resource.Error(ValidationError.INVALID_PASSPORT_IMAGES)
            }
            if (image.length() > maxSize) {
                return Resource.Error(ValidationError.INVALID_PASSPORT_IMAGES)
            }
        }

        return Resource.Success(Unit)
    }

    private fun validateAttachments(): Resource<Unit> {

        if (attachments.isEmpty()) return Resource.Error(ValidationError.EMPTY_ATTACHMENTS)
        if (attachments.size > 3) return Resource.Error(ValidationError.INVALID_ATTACHMENTS)
        attachments.forEach { attachment ->
            val extension = attachment.extension.lowercase()
            val maxSize = 512 * 1024 // 512 KB

            if (extension != "pdf") {
                return Resource.Error(ValidationError.INVALID_ATTACHMENTS)
            }
            if (attachment.length() > maxSize) {
                return Resource.Error(ValidationError.INVALID_ATTACHMENTS)
            }
        }
        return Resource.Success(Unit)
    }

    private fun validatePhoneNumber(): Resource<Unit> {
        if (number.isBlank()) return Resource.Error(ValidationError.EMPTY_PHONE_NUMBER)
        if (number.length < 9 || number.length > 15) return Resource.Error(ValidationError.INVALID_PHONE_NUMBER)
        return Resource.Success(Unit)
    }

    private fun validatePhoneCountryCode(): Resource<Unit> {
        if (countryCode.isBlank()) return Resource.Error(ValidationError.EMPTY_COUNTRY_CODE)
        if (countryCode.length < 3 || countryCode.length > 5) return Resource.Error(ValidationError.INVALID_COUNTRY_CODE)
        return Resource.Success(Unit)
    }

    private fun validateEmail(): Resource<Unit> {
        if (email.isBlank()) return Resource.Error(ValidationError.EMPTY_EMAIL)
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email)
                .matches()
        ) return Resource.Error(ValidationError.INVALID_EMAIL)
        if (email.length > 50) return Resource.Error(ValidationError.INVALID_EMAIL)
        return Resource.Success(Unit)
    }

    private fun validateDestinationCountry(): Resource<Unit> {
        if (destinationCountry.isBlank()) return Resource.Error(ValidationError.EMPTY_COUNTRY)
        return Resource.Success(Unit)
    }

    private fun validatePurposeOfVisit(): Resource<Unit> {
        if (purposeOfVisit.isBlank()) return Resource.Error(ValidationError.EMPTY_PURPOSE)
        if (purposeOfVisit.length < 5 || purposeOfVisit.length > 255) return Resource.Error(
            ValidationError.INVALID_PURPOSE
        )
        return Resource.Success(Unit)
    }

    private fun validateAdultsCount(): Resource<Unit> {
        if (adultsCount < 0 || adultsCount > 10) return Resource.Error(
            ValidationError.INVALID_ADULTS_COUNT
        )
        return Resource.Success(Unit)
    }

    private fun validateChildrenCount(): Resource<Unit> {
        if (childrenCount < 0 || childrenCount > 10) return Resource.Error(
            ValidationError.INVALID_CHILDREN_COUNT
        )
        return Resource.Success(Unit)
    }

    private fun validateMessage(): Resource<Unit> {
        if (message?.length!! > 40000) return Resource.Error(
            ValidationError.INVALID_VISA_MESSAGE
        )
        return Resource.Success(Unit)
    }



    fun createPartMap(): Map<String, RequestBody> {
        val map = mutableMapOf<String, RequestBody>()

        // Convert text fields to RequestBody (handling nullable fields)
        this.firstName.takeIf { it.isNotEmpty() }?.let {
            map["firstname"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.middleName.takeIf { it.isNotEmpty() }?.let {
            map["middlename"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.lastname.takeIf { it.isNotEmpty() }?.let {
            map["lastname"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.gender.let {
            map["gender"] = it.toString().toRequestBody("application/json".toMediaTypeOrNull())
        }
        this.birthDate.takeIf { it.isNotEmpty() }?.let {
            map["birthdate"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.passportNumber.takeIf { it.isNotEmpty() }?.let {
            map["passport_number"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.number.takeIf { it.isNotEmpty() }?.let {
            map["phone[number]"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }
        this.countryCode.takeIf { it.isNotEmpty() }?.let {
            map["phone[country_code]"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.email.takeIf { it.isNotEmpty() }?.let {
            map["contact_email"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.email.takeIf { it.isNotEmpty() }?.let {
            map["contact_email"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.destinationCountry.takeIf { it.isNotEmpty() }?.let {
            map["destination_country"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.purposeOfVisit.takeIf { it.isNotEmpty() }?.let {
            map["purpose_of_visit"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }
        this.adultsCount.let {
            map["adults_count"] = it.toString().toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.childrenCount.let {
            map["children_count"] = it.toString().toRequestBody("application/json".toMediaTypeOrNull())
        }

        this.message?.takeIf { it.isNotEmpty() }?.let {
            map["message"] = it.toRequestBody("application/json".toMediaTypeOrNull())
        }

        return map
    }


}