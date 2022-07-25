package br.com.mesainc.contacts.ui

import androidx.lifecycle.*
import br.com.mesainc.contacts.domain.GetContactsUseCase
import br.com.mesainc.contacts.domain.Result
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {

    private val _state = MutableLiveData<MainViewState>()
    private val _search = MutableLiveData("")

    val state = _state.switchMap { mainState ->
        _search.map { search ->
            if (mainState is MainViewState.Success && search.isNotEmpty()) {
                val filteredContacts = mainState.contacts.filter {
                    it.name.contains(search, ignoreCase = true)
                }

                if (filteredContacts.isNotEmpty()) {
                    MainViewState.Success(
                        filteredContacts
                    )
                } else {
                    MainViewState.EmptyFilter(search)
                }
            } else {
                mainState
            }
        }
    }

    fun getContacts() {
        _state.value = MainViewState.Loading
        viewModelScope.launch {
            val state = when (val result = getContactsUseCase()) {
                is Result.Error -> MainViewState.Error(result.exception)
                is Result.Success -> {
                    val contacts = result.data.sortedBy { it.name }
                    if (contacts.isEmpty()) {
                        MainViewState.Empty
                    } else {
                        MainViewState.Success(contacts)
                    }
                }
            }

            _state.postValue(state)
        }
    }

    fun setSearch(text: String?) {
        _search.value = text
    }
}