package com.mahmoud.altasherat.features.update_account.presentation

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.common.domain.util.error.AltasheratError
import com.mahmoud.altasherat.common.domain.util.error.ValidationError
import com.mahmoud.altasherat.common.presentation.CountryPickerBottomSheet
import com.mahmoud.altasherat.common.presentation.base.BaseFragment
import com.mahmoud.altasherat.common.presentation.base.delegators.MessageType
import com.mahmoud.altasherat.common.presentation.utils.toErrorMessage
import com.mahmoud.altasherat.databinding.FragmentProfileInfoBinding
import com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models.ListItem
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user.domain.models.User
import com.mahmoud.altasherat.features.al_tashirat_services.user.util.toFile
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class ProfileInfoFragment :
    BaseFragment<FragmentProfileInfoBinding>(FragmentProfileInfoBinding::inflate) {

    private val viewModel: ProfileInfoViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var _userCountry: Country? = null
    private var phoneCountry: Country? = null
    private var user: User? = null
    private var _countries: List<Country> = emptyList()
    private var selectedCountryPosition: Int = -1
    private var selectedPhoneCodePosition: Int = -1


    override fun FragmentProfileInfoBinding.initialize() {
        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        binding.phoneCodePicker.setOnClickListener {
            binding.phoneCodePicker.apply {
                val preSelectedPosition = if (selectedPhoneCodePosition != -1) {
                    selectedPhoneCodePosition
                } else {
                    _countries.indexOfFirst { it.id == phoneCountry?.id }
                }
                bottomSheet = CountryPickerBottomSheet(
                    _countries, preSelectedPosition
                ) { selectedCountry, position ->
                    phoneCountry = selectedCountry as Country
                    selectedPhoneCodePosition = position
                    setText(
                        resources.getString(
                            R.string.country_picker_display,
                            phoneCountry?.flag,
                            formatCountryCode(phoneCountry?.phoneCode!!)
                        )
                    )
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
            val maxCalendar = Calendar.getInstance()
            maxCalendar.add(Calendar.YEAR, -13)
            val maxDate = maxCalendar.timeInMillis

            val constraintsBuilder = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.before(maxDate))
                .setEnd(maxDate)

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birthday")
                .setSelection(maxDate)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

            datePicker.addOnPositiveButtonClickListener { selectedDateInMillis ->
                val selectedDate = Calendar.getInstance().apply {
                    timeInMillis = selectedDateInMillis
                }

                val formattedDate = String.format(
                    Locale.US,
                    "%04d-%02d-%02d",
                    selectedDate.get(Calendar.YEAR),
                    selectedDate.get(Calendar.MONTH) + 1,
                    selectedDate.get(Calendar.DAY_OF_MONTH)
                )

                binding.birthdayEdit.setText(formattedDate)
                viewModel.onAction(
                    ProfileInfoContract.ProfileInfoAction.UpdateBirthday(formattedDate)
                )
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        binding.countryEdit.setOnClickListener {
            val preSelectedPosition = if (selectedCountryPosition != -1) {
                selectedCountryPosition
            } else {
                _countries.indexOfFirst { it.id == _userCountry?.id }
            }
            bottomSheet = CountryPickerBottomSheet(
                _countries as List<ListItem>, preSelectedPosition
            ) { selectedCountry, position ->
                _userCountry = selectedCountry as Country
                selectedCountryPosition = position
                binding.countryEdit.setText(_userCountry?.flag + " " + _userCountry?.name)
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
            viewModel.onAction(ProfileInfoContract.ProfileInfoAction.UpdateCountryID(_userCountry?.id.toString()))
            viewModel.onAction(ProfileInfoContract.ProfileInfoAction.UpdateCountry(_userCountry!!))

            viewModel.onAction(
                ProfileInfoContract.ProfileInfoAction.SaveChanges
            )
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.moreButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileInfoFragment_to_manageAccountFragment)
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
                    collectFlow(viewModel.countries) { countries ->
                        if (countries.isEmpty()) return@collectFlow
                        phoneCountry = countries.find { it.phoneCode == user?.phone?.countryCode }
                        _countries = countries
                        fillFields(user)
                        hideLoading()
                    }
                }
            }
        }
        collectFlow(viewModel.userCountry) { userCountry ->
            _userCountry = userCountry
            if (userCountry != null) {
                binding.countryEdit.setText(
                    resources.getString(
                        R.string.country_picker_display, _userCountry?.flag, _userCountry?.name!!
                    )
                )
            }
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
                    showMessage(
                        resources.getString(R.string.updated_successfully_txt),
                        MessageType.SNACKBAR,
                        this
                    )
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun fillFields(user: User?) {
        if (user != null) {
            binding.firstNameEdit.setText(user.firstname)
            binding.middleNameEdit.apply {
                if (!user.middleName.isNullOrEmpty()) {
                    setText(user.middleName)
                }
            }
            binding.lastNameEdit.setText(user.lastname)
            binding.phoneEdit.setText(user.phone.number)
            if (phoneCountry != null) {
                binding.phoneCodePicker.setText(
                    resources.getString(
                        R.string.country_picker_display,
                        phoneCountry?.flag,
                        formatCountryCode(phoneCountry?.phoneCode!!)
                    )
                )
            }
            binding.emailEdit.setText(user.email)
            binding.birthdayEdit.setText(user.birthDate)
            if (_userCountry != null) {
                binding.countryEdit.setText(
                    resources.getString(
                        R.string.country_picker_display, _userCountry?.flag, _userCountry?.name!!
                    )
                )
            }
            if (user.image != null) {
                Glide.with(requireContext()).load(user.image.path!!.toUri()).circleCrop()
                    .placeholder(R.drawable.profile_place_holder)
                    .into(binding.profileImg.profileImg)

            }
        }

    }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                viewModel.onAction(
                    ProfileInfoContract.ProfileInfoAction.UpdateImage(imageUri!!.toFile(this.requireContext()))
                )
                Glide.with(requireContext()).load(imageUri)
                    .circleCrop()
                    .placeholder(R.drawable.profile_place_holder)
                    .into(binding.profileImg.profileImg)
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
                ValidationError.EMPTY_FIRSTNAME, ValidationError.INVALID_FIRSTNAME
            ) to binding.firstNameEdit, setOf(
                ValidationError.INVALID_MIDDLE_NAME,
            ) to binding.middleNameEdit, setOf(
                ValidationError.EMPTY_LASTNAME, ValidationError.INVALID_LASTNAME
            ) to binding.lastNameEdit, setOf(
                ValidationError.EMPTY_EMAIL, ValidationError.INVALID_EMAIL
            ) to binding.emailEdit, setOf(
                ValidationError.EMPTY_PHONE_NUMBER, ValidationError.INVALID_PHONE_NUMBER
            ) to binding.phoneEdit, setOf(
                ValidationError.INVALID_COUNTRY_CODE, ValidationError.EMPTY_COUNTRY_CODE
            ) to binding.phoneCodePicker
        )

        binding.firstNameEdit.error = null
        binding.lastNameEdit.error = null
        binding.emailEdit.error = null
        binding.phoneEdit.error = null
        binding.phoneCodePicker.error = null

        errors.forEach { error ->
            if (error == ValidationError.INVALID_IMAGE_EXTENSION || error == ValidationError.INVALID_IMAGE_SIZE) {
                showMessage(error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this)
            }
            errorFields.entries.find { it.key.contains(error) }?.value?.error =
                error.toErrorMessage(requireContext())
        }


    }


}