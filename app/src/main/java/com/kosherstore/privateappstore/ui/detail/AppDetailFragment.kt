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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
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
    private val screenshotAdapter by lazy { ScreenshotAdapter() }

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
        setupToolbar()
        setupScreenshotsRecycler()
        observeViewModel()
    }

    private fun setupScreenshotsRecycler() {
        binding.recyclerScreenshots.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = screenshotAdapter
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
        PagerSnapHelper().attachToRecyclerView(binding.recyclerScreenshots)
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.app_details_title)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
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
        chipCategory.text = CategoryNormalizer.normalize(app.category)
        textDescription.text = androidx.core.text.HtmlCompat.fromHtml(
            app.description,
            androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
        )
        textStatus.text = AppPresentationUtils.statusText(requireContext(), app)
        
        val showProgress = AppPresentationUtils.shouldShowProgress(app)
        progressDownload.isVisible = showProgress
        progressDownload.progress = app.downloadState.progress
        textProgress.isVisible = showProgress
        textProgress.text = getString(R.string.status_downloading, app.downloadState.progress)

        // Handle button visibility based on install status
        val primaryLabel = AppPresentationUtils.primaryActionLabelRes(app)
        buttonPrimary.setText(primaryLabel)
        buttonPrimary.isEnabled = AppPresentationUtils.isPrimaryEnabled(app)
        buttonPrimary.setOnClickListener { viewModel.onPrimaryAction(app) }

        val secondaryLabel = AppPresentationUtils.secondaryActionLabelRes(app, AppCardMode.STORE)
        if (secondaryLabel != null) {
            buttonSecondary.setText(secondaryLabel)
            buttonSecondary.isVisible = true
            buttonSecondary.setOnClickListener { viewModel.onSecondaryAction(app) }
        } else {
            buttonSecondary.isVisible = false
        }

        // Display screenshots
        if (app.screenshots.isNotEmpty()) {
            binding.recyclerScreenshots.isVisible = true
            screenshotAdapter.submitList(app.screenshots)
        } else {
            binding.recyclerScreenshots.isVisible = false
        }
        
        // Setup Read More logic
        var isExpanded = false
        binding.textReadMore.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                binding.textDescription.maxLines = Integer.MAX_VALUE
                binding.textReadMore.text = "הסתר"
            } else {
                binding.textDescription.maxLines = 4
                binding.textReadMore.text = "קרא עוד..."
            }
        }
    }

}
