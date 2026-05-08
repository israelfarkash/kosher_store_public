package com.kosherstore.privateappstore.ui.management

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.FragmentManagementBinding
import com.kosherstore.privateappstore.ui.common.AppCardMode
import com.kosherstore.privateappstore.ui.common.UiEvent
import com.kosherstore.privateappstore.ui.main.AppListAdapter
import com.kosherstore.privateappstore.util.FormatUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagementFragment : Fragment() {

    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ManagementViewModel by viewModels()

    private val adapter by lazy {
        AppListAdapter(
            mode = AppCardMode.MANAGEMENT,
            onCardClicked = { app ->
                findNavController().navigate(
                    R.id.action_management_to_detail,
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
        _binding = FragmentManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerManagedApps.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerManagedApps.adapter = adapter
        observeViewModel()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect { state ->
                        adapter.submitList(state.apps)
                        binding.textInstalledCount.text = state.summary.installedCount.toString()
                        binding.textPendingUpdates.text = state.summary.pendingUpdates.toString()
                        binding.textFreedSpace.text = FormatUtils.formatBytes(requireContext(), state.summary.freedBytes)
                        binding.textEmpty.isVisible = state.apps.isEmpty()
                    }
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
}
