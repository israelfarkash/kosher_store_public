package com.kosherstore.privateappstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.FragmentMainBinding
import com.kosherstore.privateappstore.ui.common.AppCardMode
import com.kosherstore.privateappstore.ui.common.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy {
        AppListAdapter(
            mode = AppCardMode.STORE,
            onCardClicked = { app ->
                findNavController().navigate(
                    R.id.action_main_to_detail,
                    bundleOf("packageName" to app.packageName)
                )
            },
            onPrimaryAction = viewModel::onPrimaryAction,
            onSecondaryAction = viewModel::onSecondaryAction
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupToolbarMenu()
        setupInteractions()
        observeViewModel()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupRecycler() {
        binding.recyclerApps.layoutManager = GridLayoutManager(requireContext(), spanCount())
        binding.recyclerApps.adapter = adapter
    }

    private fun setupToolbarMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
                val searchItem = menu.findItem(R.id.menu_search)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = getString(R.string.search_hint)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = true

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.onSearchChanged(newText.orEmpty())
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_refresh -> {
                        viewModel.sync()
                        true
                    }

                    R.id.menu_management -> {
                        findNavController().navigate(R.id.action_main_to_management)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupInteractions() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.sync() }
        binding.buttonCheckUpdates.setOnClickListener { viewModel.sync() }
        binding.buttonUpdateAll.setOnClickListener { viewModel.updateAll() }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect(::render)
                }
                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is UiEvent.LaunchIntent -> startActivity(event.intent)
                            is UiEvent.Message -> Snackbar.make(binding.root, event.value, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun render(state: MainViewModel.MainUiState) = with(binding) {
        swipeRefresh.isRefreshing = state.isRefreshing
        shimmerLayout.isVisible = state.isLoading && state.apps.isEmpty()
        recyclerApps.isVisible = !shimmerLayout.isVisible
        textEmpty.isVisible = !state.isLoading && state.apps.isEmpty()
        textSyncMessage.isVisible = !state.syncMessage.isNullOrBlank()
        textSyncMessage.text = state.syncMessage

        buttonCheckUpdates.isEnabled = !state.isRefreshing
        buttonUpdateAll.isVisible = state.updateCount > 0
        buttonUpdateAll.text = getString(R.string.update_all, state.updateCount)

        renderCategories(state.categories, state.selectedCategory)
        adapter.submitList(state.apps)
    }

    private fun renderCategories(categories: List<String>, selected: String?) {
        binding.chipGroupCategories.removeAllViews()

        val allChip = buildCategoryChip(
            title = getString(R.string.category_all),
            isChecked = selected == null
        ) {
            viewModel.onCategorySelected(null)
        }
        binding.chipGroupCategories.addView(allChip)

        categories.forEach { category ->
            binding.chipGroupCategories.addView(
                buildCategoryChip(category, category == selected) {
                    viewModel.onCategorySelected(category)
                }
            )
        }
    }

    private fun buildCategoryChip(title: String, isChecked: Boolean, onClick: () -> Unit): Chip {
        return Chip(requireContext()).apply {
            text = title
            isCheckable = true
            checkedIcon = null
            this.isChecked = isChecked
            setOnClickListener { onClick() }
        }
    }

    private fun spanCount(): Int {
        return if (resources.configuration.screenWidthDp >= 600) 3 else 2
    }
}
