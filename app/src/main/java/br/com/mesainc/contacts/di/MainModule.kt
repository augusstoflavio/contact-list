package br.com.mesainc.contacts.di

import br.com.mesainc.contacts.data.ContactRepositoryMock
import br.com.mesainc.contacts.domain.ContactRepository
import br.com.mesainc.contacts.domain.GetContactsUseCase
import br.com.mesainc.contacts.ui.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<ContactRepository> {
        ContactRepositoryMock()
    }

    single {
        GetContactsUseCase(get())
    }

    viewModel {
        MainActivityViewModel(get())
    }
}