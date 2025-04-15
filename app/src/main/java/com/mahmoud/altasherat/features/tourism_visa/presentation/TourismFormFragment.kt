package com.mahmoud.altasherat.features.tourism_visa.presentation

import android.net.Uri
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
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
import com.mahmoud.altasherat.databinding.FragmentTourismFormBinding
import com.mahmoud.altasherat.databinding.TourismFormPage1Binding
import com.mahmoud.altasherat.databinding.TourismFormPage2Binding
import com.mahmoud.altasherat.features.al_tashirat_services.common.domain.models.ListItem
import com.mahmoud.altasherat.features.al_tashirat_services.country.domain.models.Country
import com.mahmoud.altasherat.features.al_tashirat_services.user.util.toFile
import com.mahmoud.altasherat.features.tourism_visa.presentation.TourismFormContract.TourismFormIntent
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class TourismFormFragment :
    BaseFragment<FragmentTourismFormBinding>(FragmentTourismFormBinding::inflate) {

    private lateinit var page1Binding: TourismFormPage1Binding
    private lateinit var page2Binding: TourismFormPage2Binding
    private lateinit var viewPager2: ViewPager2
    private val viewModel: TourismFormViewmodel by viewModels()
    private lateinit var bottomSheet: CountryPickerBottomSheet
    private var _userCountry: Country? = null
    private var _visaCountry: Country? = null
    private var phoneCountry: Country? = null
    private var _countries: List<Country> = emptyList()
    private var selectedCountryPosition: Int = -1
    private var selectedVisaCountryPosition: Int = 0
    private var selectedPhoneCodePosition: Int = -1


    override fun FragmentTourismFormBinding.initialize() {
        // Inflate two views
        page1Binding = TourismFormPage1Binding.inflate(layoutInflater, null, false)
        page2Binding = TourismFormPage2Binding.inflate(layoutInflater, null, false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(tourismFormToolbar)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(true)
        }
        setupListeners()

        setupObservers()

        // Handle back navigation
        binding.tourismFormToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        val pages: List<View> = listOf(
            page1Binding.root,
            page2Binding.root
        )

        val formAdapter = TourismFormViewPagerAdapter(pages)
        binding.formViewPager.adapter = formAdapter
        viewPager2 = binding.formViewPager

        page1Binding.nextBtn.setOnClickListener {
            viewPager2.currentItem = 1
        }

    }

    private fun setupListeners() {
        page1Binding.firstNameEdit.doAfterTextChanged { text ->
            val input = text.toString()
            if (viewModel.state.value.firstName != input) {
                viewModel.onIntent(TourismFormIntent.UpdateFirstName(input))
            }

        }

        page1Binding.middleNameEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdateMiddleName(
                    it.toString()
                )
            )
        }
        page1Binding.lastNameEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdateLastName(
                    it.toString()
                )
            )
        }
        page1Binding.phoneCodePicker.apply {
            setOnClickListener {
                val preSelectedPosition = if (selectedPhoneCodePosition != -1) {
                    selectedPhoneCodePosition
                } else {
                    _countries.indexOfFirst { it.id == phoneCountry?.id }
                }
                bottomSheet = CountryPickerBottomSheet(
                    _countries,
                    preSelectedPosition,
                    isPhonePicker = true
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
                    viewModel.onIntent(
                        TourismFormIntent.UpdateCountryCode(
                            selectedCountry.phoneCode
                        )
                    )

                }
                bottomSheet.show(childFragmentManager, "PhonePickerBottomSheet")
            }

        }

        page1Binding.phoneEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdatePhoneNumber(
                    it.toString()
                )
            )
        }

        page1Binding.emailEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdateEmail(
                    it.toString()
                )
            )
        }

        page1Binding.birthdayEdit.setOnClickListener {
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

                page1Binding.birthdayEdit.setText(formattedDate)
                viewModel.onIntent(
                    TourismFormIntent.UpdateBirthDate(formattedDate)
                )
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER")
        }

        page1Binding.countryEdit.setOnClickListener {
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
                page1Binding.countryEdit.setText(_userCountry?.flag + " " + _userCountry?.name)
                viewModel.onIntent(
                    TourismFormIntent.UpdateUserCountry(
                        selectedCountry
                    )
                )

            }
            bottomSheet.show(childFragmentManager, "CountryPickerBottomSheet")
        }

        page1Binding.maleCard.setOnClickListener {
            viewModel.onIntent(TourismFormIntent.UpdateGender(0))
        }
        page1Binding.femaleCard.setOnClickListener {
            viewModel.onIntent(TourismFormIntent.UpdateGender(1))
        }

        page2Binding.passportEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdatePassportNumber(
                    it.toString()
                )
            )
        }

        page2Binding.passportImageEdit.setOnClickListener {
            pickMultipleImagesLauncher.launch("image/*")
        }

        page2Binding.visaCountryEdit.setOnClickListener {
            val preSelectedPosition = selectedVisaCountryPosition
            bottomSheet = CountryPickerBottomSheet(
                _countries as List<ListItem>, preSelectedPosition
            ) { selectedCountry, position ->
                _visaCountry = selectedCountry as Country
                page1Binding.countryEdit.setText(_visaCountry?.flag + " " + _visaCountry?.name)
                selectedVisaCountryPosition = position
                viewModel.onIntent(
                    TourismFormIntent.UpdateDestinationCountry(
                        selectedCountry.id
                    )
                )
            }
            bottomSheet.show(childFragmentManager, "VisaCountryPickerBottomSheet")
        }

        page2Binding.visaPurposeEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdatePurposeOfVisit(
                    it.toString()
                )
            )
        }

        page2Binding.visaMessageEdit.addTextChangedListener {
            viewModel.onIntent(
                TourismFormIntent.UpdateMessage(
                    it.toString()
                )
            )
        }

        page2Binding.personalFilesEdit.setOnClickListener {
            pickMultiplePdfLauncher.launch("application/pdf")
        }

        page2Binding.adultsPlusCard.setOnClickListener {
            var count = page2Binding.adultsNumberValue.text.toString().toInt()
            if (count < 10) {
                count += 1
                viewModel.onIntent(TourismFormIntent.UpdateAdultsCount(count))
            }
        }
        page2Binding.adultsMinusCard.setOnClickListener {
            var count = page2Binding.adultsNumberValue.text.toString().toInt()
            if (count > 0) {
                count -= 1
                viewModel.onIntent(TourismFormIntent.UpdateAdultsCount(count))
            }
        }

        page2Binding.childrenPlusCard.setOnClickListener {
            var count = page2Binding.childrenNumberValue.text.toString().toInt()
            if (count < 10) {
                count += 1
                viewModel.onIntent(TourismFormIntent.UpdateChildrenCount(count))
            }
        }
        page2Binding.childrenMinusCard.setOnClickListener {
            var count = page2Binding.childrenNumberValue.text.toString().toInt()
            if (count > 0) {
                count -= 1
                viewModel.onIntent(TourismFormIntent.UpdateChildrenCount(count))
            }
        }

        page2Binding.sendBtn.setOnClickListener {
            viewModel.onIntent(TourismFormIntent.SubmitForm)
        }

    }

    private fun setupObservers() {
        collectFlow(viewModel.state) { state ->
            when (state.screenState) {
                is TourismFormContract.TourismFormState.Idle -> showLoading()
                is TourismFormContract.TourismFormState.Loading -> showLoading()
                is TourismFormContract.TourismFormState.Error -> hideLoading()
                is TourismFormContract.TourismFormState.Success -> {
                    collectFlow(viewModel.countries) { countries ->
                        if (countries.isEmpty()) return@collectFlow
                        phoneCountry = countries.find { it.phoneCode == state.countryCode }
                        _countries = countries
                        fillUserData(state)
                        hideLoading()
                    }
                }
            }
        }

        collectFlow(viewModel.userCountry) { userCountry ->

            if (userCountry != null) {
                _userCountry = userCountry
                _visaCountry = userCountry
//                viewModel.onIntent(TourismFormIntent.UpdateUserCountry(_userCountry!!))
                page1Binding.countryEdit.setText(
                    resources.getString(
                        R.string.country_picker_display, _userCountry?.flag, _userCountry?.name
                    )
                )
                page2Binding.visaCountryEdit.setText(
                    resources.getString(
                        R.string.country_picker_display, _visaCountry?.flag, _visaCountry?.name
                    )
                )
            }
        }

        collectFlow(viewModel.events) { tourismFormEvent ->
            when (tourismFormEvent) {
                is TourismFormContract.TourismFormEvent.Error -> {
                    when (tourismFormEvent.error) {
                        is AltasheratError.ValidationErrors -> {
                            displayValidationErrors(tourismFormEvent.error.errors)
                        }

                        else -> {
                            val errorMessage =
                                tourismFormEvent.error.toErrorMessage(requireContext())
                            showMessage(errorMessage, MessageType.SNACKBAR, this)
                        }
                    }
                }

                is TourismFormContract.TourismFormEvent.NavigationToProfileMenu -> {
                    showMessage(
                        resources.getString(R.string.tourism_visa_created_successfully_txt),
                        MessageType.SNACKBAR,
                        this
                    )
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun fillUserData(state: TourismFormContract.TourismFormUIState) {
        if (page1Binding.firstNameEdit.text.isNullOrEmpty()) {
            page1Binding.firstNameEdit.setText(state.firstName)
        }
        if (page1Binding.middleNameEdit.text.isNullOrEmpty()) {
            page1Binding.middleNameEdit.setText(state.middleName)
        }
        if (page1Binding.lastNameEdit.text.isNullOrEmpty()) {
            page1Binding.lastNameEdit.setText(state.lastName)
        }
        if (page1Binding.phoneEdit.text.isNullOrEmpty()) {
            page1Binding.phoneEdit.setText(state.phoneNumber)
        }
        page1Binding.phoneCodePicker.setText(
            resources.getString(
                R.string.country_picker_display,
                phoneCountry?.flag,
                formatCountryCode(phoneCountry?.phoneCode!!)
            )
        )
        if (page1Binding.emailEdit.text.isNullOrEmpty()) {
            page1Binding.emailEdit.setText(state.email)
        }
        page1Binding.birthdayEdit.setText(state.birthDate)
        page1Binding.countryEdit.setText(
            resources.getString(
                R.string.country_picker_display, state.userCountry?.flag, state.userCountry?.name
            )
        )

        if (state.gender == 0) {
            page1Binding.maleCard.setCardBackgroundColor(resources.getColor(R.color.md_theme_primary))
            page1Binding.femaleCard.setCardBackgroundColor(resources.getColor(R.color.md_theme_inverseSurface))
            page1Binding.maleTxt.setTextColor(resources.getColor(R.color.md_theme_inverseSurface))
            page1Binding.femaleTxt.setTextColor(resources.getColor(android.R.color.black))
        } else if (state.gender == 1) {
            page1Binding.maleCard.setCardBackgroundColor(resources.getColor(R.color.md_theme_inverseSurface))
            page1Binding.femaleCard.setCardBackgroundColor(resources.getColor(R.color.md_theme_primary))
            page1Binding.femaleTxt.setTextColor(resources.getColor(R.color.md_theme_inverseSurface))
            page1Binding.maleTxt.setTextColor(resources.getColor(android.R.color.black))
        }

        if (page2Binding.passportEdit.text.isNullOrEmpty()) {
            page2Binding.passportEdit.setText(state.passportNumber)
        }
        //passportImages
        page2Binding.visaCountryEdit.setText(
            resources.getString(
                R.string.country_picker_display,
                _countries.find { it.id == state.destinationCountry }?.flag,
                _countries.find { it.id == state.destinationCountry }?.name
            )
        )
        if (page2Binding.visaPurposeEdit.text.isNullOrEmpty()) {
            page2Binding.visaPurposeEdit.setText(state.purposeOfVisit)
        }
        if (page2Binding.visaMessageEdit.text.isNullOrEmpty()) {
            page2Binding.visaMessageEdit.setText(state.message)
        }
        //passport attachments
        page2Binding.adultsNumberValue.text = state.adultsCount.toString()
        page2Binding.childrenNumberValue.text = state.childrenCount.toString()
    }

    private val pickMultipleImagesLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
                val files = uris.mapNotNull { uri -> uri.toFile(requireContext()) }
                page2Binding.passportImageEdit.setText("${files.size} images selected")
                viewModel.onIntent(TourismFormIntent.UpdatePassportImages(files))
            }
        }

    private val pickMultiplePdfLauncher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
                val files = uris.mapNotNull { uri -> uri.toFile(requireContext()) }
                page2Binding.personalFilesEdit.setText("${files.size} files selected")
                viewModel.onIntent(TourismFormIntent.UpdateAttachments(files))
            }
        }

    private fun displayValidationErrors(errors: List<ValidationError>) {
        val errorFields = mapOf(
            setOf(
                ValidationError.EMPTY_FIRSTNAME, ValidationError.INVALID_FIRSTNAME
            ) to page1Binding.firstNameEdit,
            setOf(
                ValidationError.EMPTY_MIDDLE_NAME, ValidationError.INVALID_MIDDLE_NAME,
            ) to page1Binding.middleNameEdit,
            setOf(
                ValidationError.EMPTY_LASTNAME, ValidationError.INVALID_LASTNAME
            ) to page1Binding.lastNameEdit,
            setOf(
                ValidationError.EMPTY_PHONE_NUMBER, ValidationError.INVALID_PHONE_NUMBER
            ) to page1Binding.phoneEdit,
            setOf(
                ValidationError.INVALID_COUNTRY_CODE, ValidationError.EMPTY_COUNTRY_CODE
            ) to page1Binding.phoneCodePicker,
            setOf(
                ValidationError.EMPTY_EMAIL, ValidationError.INVALID_EMAIL
            ) to page1Binding.emailEdit,
            setOf(
                ValidationError.EMPTY_BIRTHDATE
            ) to page1Binding.birthdayEdit,
            setOf(
                ValidationError.EMPTY_PASSPORT_NUMBER, ValidationError.INVALID_PASSPORT_NUMBER
            ) to page2Binding.passportEdit,
            setOf(
                ValidationError.EMPTY_PASSPORT_IMAGES, ValidationError.INVALID_PASSPORT_IMAGES
            ) to page2Binding.passportImageEdit,
            setOf(
                ValidationError.EMPTY_PURPOSE, ValidationError.INVALID_PURPOSE
            ) to page2Binding.visaPurposeEdit,
            setOf(
                ValidationError.INVALID_VISA_MESSAGE
            ) to page2Binding.visaMessageEdit,
            setOf(
                ValidationError.EMPTY_ATTACHMENTS, ValidationError.INVALID_ATTACHMENTS
            ) to page2Binding.personalFilesEdit,
        )

        page1Binding.firstNameEdit.error = null
        page1Binding.middleNameEdit.error = null
        page1Binding.lastNameEdit.error = null
        page1Binding.phoneEdit.error = null
        page1Binding.phoneCodePicker.error = null
        page1Binding.emailEdit.error = null
        page1Binding.birthdayEdit.error = null
        page2Binding.passportEdit.error = null
        page2Binding.passportImageEdit.error = null
        page2Binding.visaPurposeEdit.error = null
        page2Binding.visaMessageEdit.error = null
        page2Binding.personalFilesEdit.error = null


        errors.forEach { error ->
            if (error == ValidationError.INVALID_ADULTS_COUNT || error == ValidationError.INVALID_CHILDREN_COUNT) {
                showMessage(error.toErrorMessage(requireContext()), MessageType.SNACKBAR, this)
            }
            errorFields.entries.find { it.key.contains(error) }?.value?.error =
                error.toErrorMessage(requireContext())
        }


    }


}


