package com.kosherstore.privateappstore.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
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
            findNavController().navigate(
                R.id.action_main_to_categoryDetail,
                bundleOf("categoryName" to category)
            )
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

    private val pagerAdapter by lazy {
        MainPagerAdapter(
            onBindApps = { recycler, shimmer, empty, syncCard, syncText ->
                val spanCount = if (resources.configuration.screenWidthDp >= 600) 2 else 1
                recycler.layoutManager = GridLayoutManager(requireContext(), spanCount)
                recycler.adapter = adapter
                recycler.itemAnimator = null

                val state = viewModel.uiState.value
                val showShimmer = state.isLoading && state.apps.isEmpty()
                val showEmpty = !state.isLoading && state.apps.isEmpty() && state.query.isNotBlank()

                shimmer.isVisible = showShimmer
                empty.isVisible = showEmpty
                
                syncCard.isVisible = !state.syncMessage.isNullOrBlank()
                syncText.text = state.syncMessage ?: ""
                
                adapter.submitList(state.apps)
            },
            onBindCategories = { recycler ->
                recycler.layoutManager = GridLayoutManager(requireContext(), 2)
                recycler.adapter = categoryAdapter
                
                val state = viewModel.uiState.value
                categoryAdapter.submitList(CategoryDisplayData.mapFromNames(state.categories))
            }
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
        setupViewPager()
        setupInteractions()
        observeViewModel()
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = pagerAdapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "כל האפליקציות" else "קטגוריות"
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.cardSearch.isVisible = (position == 0)
                if (position == 1) {
                    binding.editSearch.text?.clear()
                    binding.editSearch.clearFocus()
                    val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? android.view.inputmethod.InputMethodManager
                    imm?.hideSoftInputFromWindow(binding.editSearch.windowToken, 0)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                binding.swipeRefresh.isEnabled = (state == ViewPager2.SCROLL_STATE_IDLE)
            }
        })
    }

    private fun setupInteractions() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.sync() }

        binding.editSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s?.toString().orEmpty()
                viewModel.onSearchChanged(text)
                if (text.isNotBlank() && binding.editSearch.hasFocus() && binding.viewPager.currentItem != 0) {
                    binding.viewPager.currentItem = 0
                }
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.buttonSettings.setOnClickListener {
            findNavController().navigate(R.id.settingsFragment)
        }

        binding.buttonDownloads.setOnClickListener {
            findNavController().navigate(R.id.downloadsFragment)
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

        adapter.submitList(state.apps)
        categoryAdapter.submitList(CategoryDisplayData.mapFromNames(state.categories))

        val recyclerApps = viewPager.findViewById<RecyclerView>(R.id.recyclerApps)
        val shimmer = viewPager.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
        val empty = viewPager.findViewById<View>(R.id.layoutEmpty)
        val syncCard = viewPager.findViewById<MaterialCardView>(R.id.cardSyncMessage)
        val syncText = viewPager.findViewById<TextView>(R.id.textSyncMessage)

        if (recyclerApps != null) {
            val showShimmer = state.isLoading && state.apps.isEmpty()
            val showEmpty = !state.isLoading && state.apps.isEmpty() && state.query.isNotBlank()
            
            shimmer?.isVisible = showShimmer
            empty?.isVisible = showEmpty
            
            if (syncCard != null && syncText != null) {
                syncCard.isVisible = !state.syncMessage.isNullOrBlank()
                syncText.text = state.syncMessage ?: ""
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
