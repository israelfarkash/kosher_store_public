package com.kosherstore.privateappstore.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.databinding.FragmentAppDetailBinding
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.ui.common.AppCardMode
import com.kosherstore.privateappstore.ui.common.AppPresentationUtils
import com.kosherstore.privateappstore.ui.common.UiEvent
import com.kosherstore.privateappstore.util.CategoryNormalizer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppDetailFragment : Fragment() {

    private var _binding: FragmentAppDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    viewModel.appState.collect { app ->
                        app?.let(::render)
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

    private fun render(app: StoreApp) = with(binding) {
        imageIcon.load(app.iconUrl)
        textName.text = app.name
        textVersion.text = app.versionName ?: "v${app.versionCode}"
        textPackageName.text = app.packageName
        textSize.text = app.size
        textCategory.text = CategoryNormalizer.normalize(app.category)
        textDescription.text = app.description
        textStatus.text = AppPresentationUtils.statusText(requireContext(), app)
        progressDownload.isVisible = AppPresentationUtils.shouldShowProgress(app)
        progressDownload.progress = app.downloadState.progress
        textProgress.isVisible = AppPresentationUtils.shouldShowProgress(app)
        textProgress.text = "${app.downloadState.progress}%"

        buttonPrimary.setText(AppPresentationUtils.primaryActionLabelRes(app))
        buttonPrimary.isEnabled = AppPresentationUtils.isPrimaryEnabled(app)
        buttonPrimary.setOnClickListener { viewModel.onPrimaryAction(app) }

        val secondaryLabel = AppPresentationUtils.secondaryActionLabelRes(app, AppCardMode.STORE)
        buttonSecondary.isVisible = secondaryLabel != null
        secondaryLabel?.let(buttonSecondary::setText)
        buttonSecondary.setOnClickListener { viewModel.onSecondaryAction(app) }

        buttonOpen.isVisible = app.isInstalled
        buttonOpen.setOnClickListener { viewModel.openApp() }
    }
}
