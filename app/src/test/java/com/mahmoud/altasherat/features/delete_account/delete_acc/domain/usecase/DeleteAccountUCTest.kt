package com.mahmoud.altasherat.features.delete_account.delete_acc.domain.usecase

import com.mahmoud.altasherat.features.delete_account.delete_acc.data.repository.DeleteAccountRepository
import com.mahmoud.altasherat.features.delete_account.delete_acc.domain.repository.remote.IDeleteAccountRemoteDS
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class DeleteAccountUCTest {

    private lateinit var deleteAccountUC: DeleteAccountUC
    private lateinit var deleteAccRepository: DeleteAccountRepository

    private val deleteAccRemoteDS: IDeleteAccountRemoteDS = mock()

    @Before
    fun setUp() {
        deleteAccRepository = DeleteAccountRepository(deleteAccRemoteDS)
        deleteAccountUC = DeleteAccountUC(deleteAccRepository)
    }


}