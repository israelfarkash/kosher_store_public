package com.kosherstore.privateappstore.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.FragmentCategoryDetailBinding
import com.kosherstore.privateappstore.ui.common.AppCardMode
import com.kosherstore.privateappstore.ui.main.AppListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryDetailFragment : Fragment() {

    private var _binding: FragmentCategoryDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryDetailViewModel by viewModels()

    private val adapter by lazy {
        AppListAdapter(
            mode = AppCardMode.STORE,
            onCardClicked = { app ->
                findNavController().navigate(
                    R.id.action_categoryDetail_to_detail,
                    bundleOf("packageName" to app.packageName)
                )
            },
            onPrimaryAction = { app ->
                viewModel.onPrimaryAction(
                    app = app,
                    launchIntentConsumer = { intent -> startActivity(intent) },
                    messageConsumer = { msg -> Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show() }
                )
            },
            onSecondaryAction = viewModel::onSecondaryAction
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupRecycler()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.title = viewModel.categoryName
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecycler() {
        val spanCount = if (resources.configuration.screenWidthDp >= 600) 2 else 1
        binding.recyclerApps.layoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.recyclerApps.adapter = adapter
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appsState.collect { apps ->
                    adapter.submitList(apps)
                    binding.layoutEmpty.isVisible = apps.isEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
