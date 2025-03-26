package com.mahmoud.altasherat.features.profile_info.presentation

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentProfileInfoBinding
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.language_country.domain.models.ListItem
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user_services.util.toFile
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ProfileInfoFragment :
    BaseFragment<FragmentProfileInfoBinding>(FragmentProfileInfoBinding::inflate) {

    private val viewModel: ProfileInfoViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var userCountry: Country? = null
    private var phoneCountry: Country? = null
    private var user: User? = null
    private var _countries: List<Country>? = null

    override fun FragmentProfileInfoBinding.initialize() {
        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        binding.phoneCodePicker.setOnClickListener {
            binding.phoneCodePicker.apply {
                bottomSheet = CountryPickerBottomSheet(
                    _countries as List<ListItem>, phoneCountry!!.id.minus(1)
                ) { selectedCountry ->
                    phoneCountry = selectedCountry as Country
                    setText(phoneCountry?.flag + " (" + phoneCountry?.phoneCode + ")")
                    viewModel.onAction(
                        ProfileInfoContract.ProfileInfoAction.UpdateCountryCode(
                            selectedCountry.phoneCode
                        )
                    )

                }
            }
            bottomSheet.show(childFragmentManager, "PhonePickerBottomSheet")
        }


        binding.birthdayEdit.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this.requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                    binding.birthdayEdit.setText(formattedDate)
                    viewModel.onAction(
                        ProfileInfoContract.ProfileInfoAction.UpdateBirthday(
                            formattedDate
                        )
                    )
                }, year, month, day
            )
            datePickerDialog.show()
        }

        binding.countryEdit.setOnClickListener {
            bottomSheet = CountryPickerBottomSheet(
                _countries as List<ListItem>, userCountry!!.id.minus(1)
            ) { selectedCountry ->
                userCountry = selectedCountry as Country
                binding.countryEdit.setText(userCountry?.flag + " " + userCountry?.name)
                viewModel.onAction(
                    ProfileInfoContract.ProfileInfoAction.UpdateCountryID(
                        selectedCountry.id.toString()
                    )
                )

            }
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }

        binding.profileImg.editIcon.setOnClickListener {
            openImagePicker()
        }

        binding.firstNameEdit.addTextChangedListener {
            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.UpdateFirstName(
                    it.toString()
                )
            )
        }
        binding.middleNameEdit.addTextChangedListener {
            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.UpdateMiddleName(
                    it.toString()
                )
            )
        }
        binding.lastNameEdit.addTextChangedListener {
            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.UpdateLastName(
                    it.toString()
                )
            )
        }
        binding.emailEdit.addTextChangedListener {
            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.UpdateEmail(
                    it.toString()
                )
            )
        }

        binding.phoneEdit.addTextChangedListener {
            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.UpdatePhoneNumber(
                    it.toString()
                )
            )
        }
        binding.saveBtn.setOnClickListener {

            viewModel.onAction(ProfileInfoContract.ProfileInfoAction.UpdateCountryCode(phoneCountry?.phoneCode!!))
            viewModel.onAction(ProfileInfoContract.ProfileInfoAction.UpdateCountryID(userCountry?.id.toString()))
            viewModel.onAction(ProfileInfoContract.ProfileInfoAction.UpdateCountry(userCountry!!))

            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.SaveChanges
            )
        }


    }

    private fun setupObservers() {
        collectFlow(viewModel.state) { state ->
            when (state) {
                is ProfileInfoContract.ProfileInfoState.Error -> hideLoading()
                is ProfileInfoContract.ProfileInfoState.Idle -> showLoading()
                is ProfileInfoContract.ProfileInfoState.Loading -> showLoading()
                is ProfileInfoContract.ProfileInfoState.Success -> {
                    user = state.userResponse
                    fillFields(user)
                    hideLoading()
                }
            }
        }

        collectFlow(viewModel.countries) { countries ->
            if (countries.isEmpty()) return@collectFlow
            phoneCountry = countries.find { it.phoneCode == user?.phone?.countryCode }
            binding.phoneCodePicker.setText(phoneCountry?.flag + " (" + phoneCountry?.phoneCode + ")")
            _countries = countries

        }
        collectFlow(viewModel.userCountry) { userCountry ->
            this.userCountry = userCountry
            Log.d("USER_COUNTRY", userCountry.toString())
            binding.countryEdit.setText(userCountry?.flag + " " + userCountry?.name)

        }

        collectFlow(viewModel.events) { profileInfoEvent ->
            when (profileInfoEvent) {
                is ProfileInfoContract.ProfileInfoEvent.Error -> {
                    when (profileInfoEvent.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(profileInfoEvent.error.errors)
                        }

                        else -> {
                            val errorMessage =
                                profileInfoEvent.error.toErrorMessage(requireContext())
                            showMessage(errorMessage, MessageType.SNACKBAR, this)
                        }
                    }
                }

                is ProfileInfoContract.ProfileInfoEvent.NavigationToProfileMenu -> {
                    showMessage("Saved Successfully", MessageType.SNACKBAR, this)
                }

            }
        }
    }

    private fun fillFields(user: User?) {
        if (user != null) {
            binding.firstNameEdit.setText(user.firstname)
            binding.middleNameEdit.apply {
                if (user.middleName.isNotEmpty()) {
                    setText(user.middleName)
                }
            }
            binding.lastNameEdit.setText(user.lastname)
            binding.phoneEdit.setText(user.phone.number)
            binding.phoneCodePicker.setText(phoneCountry?.flag + " (" + user.phone.countryCode + ")")
            binding.emailEdit.setText(user.email)
            binding.birthdayEdit.apply {
                if (user.birthDate.isNotEmpty()) {
                    setText(user.birthDate)
                }
            }
            binding.countryEdit.setText("${userCountry?.flag} ${userCountry?.name}")
            binding.profileImg.profileImg.apply {
                if (user.image.isNotEmpty()) {
                    setImageURI(user.image.toUri())
                } else {
                    setImageDrawable(resources.getDrawable(R.drawable.profile_place_holder))
                }
            }

        }

    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                Log.d("IMAGE_URI", imageUri?.toFile(this.requireContext()).toString())
                ProfileInfoContract.ProfileInfoAction.UpdateImage(imageUri!!.toFile(this.requireContext()))
                binding.profileImg.profileImg.setImageURI(imageUri)
            }
        }

    private fun openImagePicker() {
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Intent(MediaStore.ACTION_PICK_IMAGES)
        } else {
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
        }
        pickImageLauncher.launch(intent)
    }

    private fun displayValidationErrors(errors: List<ValidationError>) {
        val errorFields = mapOf(
            setOf(
                ValidationError.EMPTY_FIRSTNAME,
                ValidationError.INVALID_FIRSTNAME
            ) to binding.firstNameEdit,
            setOf(
                ValidationError.INVALID_MIDDLE_NAME,
            ) to binding.middleNameEdit,
            setOf(
                ValidationError.EMPTY_LASTNAME,
                ValidationError.INVALID_LASTNAME
            ) to binding.lastNameEdit,
            setOf(
                ValidationError.EMPTY_EMAIL,
                ValidationError.INVALID_EMAIL
            ) to binding.emailEdit,
            setOf(
                ValidationError.EMPTY_PHONE_NUMBER,
                ValidationError.INVALID_PHONE_NUMBER
            ) to binding.phoneEdit,
            setOf(
                ValidationError.INVALID_COUNTRY_CODE,
                ValidationError.EMPTY_COUNTRY_CODE
            ) to binding.phoneCodePicker
        )

        binding.firstNameEdit.error = null
        binding.lastNameEdit.error = null
        binding.emailEdit.error = null
        binding.phoneEdit.error = null
        binding.phoneCodePicker.error = null

        errors.forEach { error ->
            errorFields.entries.find { it.key.contains(error) }?.value?.error =
                error.toErrorMessage(requireContext())
        }


    }


}