package com.mahmoud.altasherat.features.update_account.presentation

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
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
import java.util.Locale

@AndroidEntryPoint
class ProfileInfoFragment :
    BaseFragment<FragmentProfileInfoBinding>(FragmentProfileInfoBinding::inflate) {

    private val viewModel: ProfileInfoViewModel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var _userCountry: Country? = null
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
                    _countries!!, phoneCountry!!.id.minus(1)
                ) { selectedCountry ->
                    phoneCountry = selectedCountry as Country
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
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val maxCalendar = Calendar.getInstance()
            maxCalendar.add(Calendar.YEAR, -13)
            val maxDate = maxCalendar.timeInMillis

            val datePickerDialog = DatePickerDialog(
                this.requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        Locale.US, "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay
                    )
                    binding.birthdayEdit.setText(formattedDate)
                    viewModel.onAction(
                        ProfileInfoContract.ProfileInfoAction.UpdateBirthday(
                            formattedDate
                        )
                    )
                }, year, month, day
            )
            datePickerDialog.window?.setBackgroundDrawableResource(R.color.splash_screen_background)
            datePickerDialog.datePicker.maxDate = maxDate
            datePickerDialog.show()
        }

        binding.countryEdit.setOnClickListener {
            bottomSheet = CountryPickerBottomSheet(
                _countries as List<ListItem>, _userCountry!!.id.minus(1)
            ) { selectedCountry ->
                _userCountry = selectedCountry as Country
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
                Glide.with(requireContext()).load(user.image.path!!.toUri()).centerCrop()
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
                Glide.with(requireContext()).load(imageUri).centerCrop()
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