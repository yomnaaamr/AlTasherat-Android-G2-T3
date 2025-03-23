package com.mahmoud.altasherat.features.menu.data

import android.content.Context
import com.mahmoud.altasherat.R
import com.mahmoud.altasherat.features.menu.data.models.NavigationItem

object MenuDataSource {

    fun getNavigationItems(context: Context): List<NavigationItem> = listOf(
        NavigationItem(
            id = 1,
            title = context.getString(R.string.sign_in_text),
            iconRes = R.drawable.ic_login,
            destinationId = R.id.authFragment
        ),
        NavigationItem(
            id = 2,
            title = context.getString(R.string.change_password),
            iconRes = R.drawable.ic_editpass,
            destinationId = R.id.changePasswordFragment,
            requiresAuth = true
        ),
        NavigationItem(
            id = 3,
            title = context.getString(R.string.about_us),
            iconRes = R.drawable.ic_about_us,
            destinationId = R.id.aboutUsFragment,
        ),
        NavigationItem(
            id = 4,
            title = context.getString(R.string.contact_us),
            iconRes = R.drawable.ic_contact_us,
            destinationId = R.id.contactUsFragment,
        ),
        NavigationItem(
            id = 5,
            title = context.getString(R.string.terms_and_conditions),
            iconRes = R.drawable.ic_terms,
            destinationId = R.id.termsAndConditionsFragment,
        ),
        NavigationItem(
            id = 6,
            title = context.getString(R.string.privacy_policy),
            iconRes = R.drawable.ic_policy,
            destinationId = R.id.privacyPolicyFragment,
        ),
        NavigationItem(
            id = 7,
            title = context.getString(R.string.language),
            iconRes = R.drawable.ic_language,
            destinationId = R.id.changeLanguageFragment,
            requiresAuth = true
        ),

        )
}