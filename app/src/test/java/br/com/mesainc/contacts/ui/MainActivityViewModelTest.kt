package br.com.mesainc.contacts.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mesainc.contacts.domain.Contact
import br.com.mesainc.contacts.domain.ContactRepository
import br.com.mesainc.contacts.domain.GetContactsUseCase
import br.com.mesainc.contacts.domain.Result
import br.com.mesainc.contacts.ext.getOrAwaitValue
import br.com.mesainc.contacts.utils.MainCoroutineRule
import br.com.mesainc.contacts.utils.MockkClearRule
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var mockkRule = MockkClearRule()

    private var repository: ContactRepository = mockk()

    private var useCase: GetContactsUseCase = GetContactsUseCase(repository)

    private var viewModel: MainActivityViewModel = MainActivityViewModel(useCase)

    private val contacts = listOf(
        Contact(
            "Augusto",
            "(79) 9 98583412",
            "augusto@augusto.com.br"
        ),
        Contact(
            "Flávio",
            "(79) 9 98583412",
            "flavio@flavio.com.br"
        ),
        Contact(
            "Mendonça",
            "(79) 9 98583412",
            "mendonca@mendonca.com.br"
        )
    )

    @Test
    fun `when contact repository return empty list then viewState is Empty`() {
        //arrange
        coEvery { repository.getContacts() } returns Result.Success(listOf())

        //act
        viewModel.getContacts()

        //assert
        val state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.Empty::class.java)
    }

    @Test
    fun `when call getContacts, viewState change to Loading`() {
        //arrange
        coEvery { repository.getContacts() } returns Result.Success(listOf())

        //act
        mainCoroutineRule.pauseDispatcher()
        viewModel.getContacts()

        //assert
        var state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.Loading::class.java)

        mainCoroutineRule.resumeDispatcher()
        state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.Empty::class.java)
    }

    @Test
    fun `when contact repository return Error, viewState is Error`() {
        //arrange
        coEvery { repository.getContacts() } returns Result.Error(Exception())

        //act
        viewModel.getContacts()

        //assert
        val state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.Error::class.java)
    }

    @Test
    fun `when search is invalid, viewState is EmptySearch`() {
        //arrange
        coEvery { repository.getContacts() } returns Result.Success(contacts)

        //act
        viewModel.getContacts()
        viewModel.setSearch("Aracaju")

        //assert
        val state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.EmptyFilter::class.java)
    }

    @Test
    fun `when search is valid, viewState is Success`() {
        //arrange
        coEvery { repository.getContacts() } returns Result.Success(contacts)

        //act
        val search = contacts.first().name
        viewModel.getContacts()
        viewModel.setSearch(search)

        //assert
        val state = viewModel.state.getOrAwaitValue()
        Truth.assertThat(state).isInstanceOf(MainViewState.Success::class.java)
        if (state is MainViewState.Success) {
            val filteredContacts = state.contacts.filter { it.name.contains(search, ignoreCase = true) }

            Truth.assertThat(state.contacts).isEqualTo(filteredContacts)
        }
    }
}