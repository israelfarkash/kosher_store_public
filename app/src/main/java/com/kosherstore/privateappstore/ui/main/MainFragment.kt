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

    private val categoryAdapter by lazy {
        CategoryAdapter { category ->
            viewModel.onCategorySelected(category)
        }
    }

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
        binding.recyclerApps.itemAnimator = null

        binding.recyclerCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerCategories.adapter = categoryAdapter
    }

    private fun setupInteractions() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.sync() }

        
        binding.editSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onSearchChanged(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.buttonSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.buttonBack.setOnClickListener {
            viewModel.onCategorySelected(null)
            binding.editSearch.text?.clear()
        }
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
        
        val isSearching = state.query.isNotBlank()
        val hasCategory = state.selectedCategory != null
        val showAppList = hasCategory || isSearching
        
        val showShimmer = state.isLoading && state.apps.isEmpty()
        val showEmpty = !state.isLoading && state.apps.isEmpty() && showAppList
        
        shimmerLayout.isVisible = showShimmer
        
        // Toggle between Category Grid and App List
        recyclerCategories.isVisible = !showAppList && !showShimmer
        recyclerApps.isVisible = showAppList && !showShimmer && !showEmpty
        
        layoutEmpty.isVisible = showEmpty
        textSyncMessage.isVisible = !state.syncMessage.isNullOrBlank()
        textSyncMessage.text = state.syncMessage


        // Search bar UI
        buttonBack.isVisible = showAppList
        imageSearchIcon.isVisible = !showAppList
        if (!showAppList) {
            categoryAdapter.submitList(CategoryDisplayData.mapFromNames(state.categories))
        } else {
            adapter.submitList(state.apps)
        }
    }

    private fun spanCount(): Int {
        return if (resources.configuration.screenWidthDp >= 600) 2 else 1
    }
}

