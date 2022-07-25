package br.com.mesainc.contacts.ui

import br.com.mesainc.contacts.domain.Contact
import java.lang.Exception

sealed class MainViewState {

    object Loading : MainViewState()
    data class Error(val exception: Exception): MainViewState()
    data class Success(val contacts: List<Contact>): MainViewState()
    object Empty : MainViewState()
    class EmptyFilter(val search: String): MainViewState()
}