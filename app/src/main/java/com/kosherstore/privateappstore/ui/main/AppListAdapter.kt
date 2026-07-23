package com.kosherstore.privateappstore.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kosherstore.privateappstore.databinding.ItemAppCardBinding
import com.kosherstore.privateappstore.domain.model.StoreApp
import com.kosherstore.privateappstore.ui.common.AppCardMode
import com.kosherstore.privateappstore.ui.common.AppPresentationUtils
import com.kosherstore.privateappstore.util.CategoryNormalizer

class AppListAdapter(
    private val mode: AppCardMode,
    private val onCardClicked: (StoreApp) -> Unit,
    private val onPrimaryAction: (StoreApp) -> Unit,
    private val onSecondaryAction: (StoreApp) -> Unit
) : ListAdapter<StoreApp, AppListAdapter.AppViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppViewHolder(
        private val binding: ItemAppCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(app: StoreApp) = with(binding) {
            imageIcon.load(app.iconUrl)
            textName.text = app.name
            textMeta.text = app.size
            textCategory.text = CategoryNormalizer.normalize(app.category)
            textStatus.text = AppPresentationUtils.statusText(root.context, app)

            progressDownload.isVisible = AppPresentationUtils.shouldShowProgress(app)
            progressDownload.progress = app.downloadState.progress
            textProgress.isVisible = AppPresentationUtils.shouldShowProgress(app)
            textProgress.text = "${app.downloadState.progress}%"

            buttonPrimary.setText(AppPresentationUtils.primaryActionLabelRes(app))
            buttonPrimary.isEnabled = AppPresentationUtils.isPrimaryEnabled(app)

            val secondaryLabel = AppPresentationUtils.secondaryActionLabelRes(app, mode)
            if (secondaryLabel != null) {
                buttonSecondary.setText(secondaryLabel)
                buttonSecondary.isVisible = true
            } else {
                buttonSecondary.isVisible = false
            }

            root.setOnClickListener { onCardClicked(app) }
            buttonPrimary.setOnClickListener { onPrimaryAction(app) }
            buttonSecondary.setOnClickListener { onSecondaryAction(app) }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<StoreApp>() {
        override fun areItemsTheSame(oldItem: StoreApp, newItem: StoreApp): Boolean {
            return oldItem.packageName == newItem.packageName
        }

        override fun areContentsTheSame(oldItem: StoreApp, newItem: StoreApp): Boolean {
            return oldItem == newItem
        }
    }
}
