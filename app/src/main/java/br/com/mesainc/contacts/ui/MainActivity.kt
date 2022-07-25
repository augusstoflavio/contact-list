package br.com.mesainc.contacts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mesainc.contacts.R
import br.com.mesainc.contacts.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val _viewModel: MainActivityViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    private val _contactAdapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _viewModel.getContacts()
        setupView()
        setupListeners()
        setupObservables()
    }

    private fun setupView() {
        binding.rvListContact.adapter = _contactAdapter
        binding.rvListContact.layoutManager = LinearLayoutManager(this)
    }

    private fun setupListeners() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            _viewModel.setSearch(text.toString())
        }
    }

    private fun setupObservables() {
        _viewModel.state.observe(this, ::handleState)
    }

    private fun handleState(mainViewState: MainViewState?) {
        when(mainViewState) {
            is MainViewState.Empty -> setupEmptyState()
            is MainViewState.Error -> setupErrorState(mainViewState)
            is MainViewState.Loading -> setupLoadingState()
            is MainViewState.Success -> setupSuccessState(mainViewState)
            is MainViewState.EmptyFilter -> setupEmptyFilterState(mainViewState)
            null -> setupEmptyState()
        }
    }

    private fun setupSuccessState(mainViewState: MainViewState.Success) {
        with(binding) {
            pbLoading.isVisible = false
            tvEmptyMsg.isVisible = false
            tvEmptySearchMsg.isVisible = false
            rvListContact.isVisible = true
            _contactAdapter.submitList(mainViewState.contacts)
        }
    }

    private fun setupLoadingState() {
        with(binding) {
            pbLoading.isVisible = true
            tvEmptyMsg.isVisible = false
            tvEmptySearchMsg.isVisible = false
            rvListContact.isVisible = true
        }
    }

    private fun setupErrorState(mainViewState: MainViewState.Error) {
        with(binding) {
            pbLoading.isVisible = false
        }
        Toast.makeText(this, mainViewState.exception.message ?: "Ocorreu um erro", Toast.LENGTH_SHORT).show()
    }

    private fun setupEmptyState() {
        with(binding) {
            pbLoading.isVisible = false
            tvEmptyMsg.isVisible = true
            rvListContact.isVisible = false
            tvEmptySearchMsg.isVisible = false
        }
    }

    private fun setupEmptyFilterState(mainViewState: MainViewState.EmptyFilter) {
        with(binding) {
            pbLoading.isVisible = false
            tvEmptyMsg.isVisible = false
            rvListContact.isVisible = false
            tvEmptySearchMsg.isVisible = true
            tvEmptySearchMsg.text = getString(R.string.empty_search_msg, mainViewState.search)
        }
    }
}