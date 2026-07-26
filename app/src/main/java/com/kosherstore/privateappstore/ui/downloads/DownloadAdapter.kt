package com.kosherstore.privateappstore.ui.downloads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.kosherstore.privateappstore.databinding.ItemDownloadTaskBinding
import com.kosherstore.privateappstore.domain.model.DownloadStatus

class DownloadAdapter(
    private val onItemClick: (String) -> Unit,
    private val onCancelClick: (String) -> Unit,
    private val onClearClick: (String) -> Unit
) : ListAdapter<DownloadTaskUiModel, DownloadAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDownloadTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDownloadTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: DownloadTaskUiModel) {
            binding.root.setOnClickListener { onItemClick(task.packageName) }
            binding.textName.text = task.appName
            binding.imageIcon.load(task.iconUrl) {
                crossfade(true)
                transformations(RoundedCornersTransformation(16f))
            }

            when (task.status) {
                DownloadStatus.PENDING -> {
                    binding.textStatus.text = "בהמתנה..."
                    binding.progressBar.isIndeterminate = true
                    binding.progressBar.visibility = View.VISIBLE
                    setupCancelButton(task.packageName)
                }
                DownloadStatus.DOWNLOADING -> {
                    binding.textStatus.text = "מוריד... ${task.progress}%"
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.progress = task.progress
                    binding.progressBar.visibility = View.VISIBLE
                    setupCancelButton(task.packageName)
                }
                DownloadStatus.PAUSED -> {
                    binding.textStatus.text = "הושהה"
                    binding.progressBar.visibility = View.GONE
                    setupClearButton(task.packageName)
                }
                DownloadStatus.VERIFYING -> {
                    binding.textStatus.text = "מאמת קובץ..."
                    binding.progressBar.isIndeterminate = true
                    binding.progressBar.visibility = View.VISIBLE
                    setupCancelButton(task.packageName)
                }
                DownloadStatus.COMPLETED -> {
                    binding.textStatus.text = "הורדה הושלמה"
                    binding.progressBar.visibility = View.GONE
                    setupClearButton(task.packageName)
                }
                DownloadStatus.FAILED -> {
                    binding.textStatus.text = task.errorMessage ?: "הורדה נכשלה"
                    binding.textStatus.setTextColor(binding.root.context.getColor(android.R.color.holo_red_dark))
                    binding.progressBar.visibility = View.GONE
                    setupClearButton(task.packageName)
                }
                else -> {
                    binding.textStatus.text = "מצב לא ידוע"
                    binding.progressBar.visibility = View.GONE
                    setupClearButton(task.packageName)
                }
            }
        }

        private fun setupCancelButton(packageName: String) {
            binding.buttonAction.setImageResource(com.kosherstore.privateappstore.R.drawable.ic_close_white)
            binding.buttonAction.setOnClickListener { onCancelClick(packageName) }
        }

        private fun setupClearButton(packageName: String) {
            binding.buttonAction.setImageResource(com.kosherstore.privateappstore.R.drawable.ic_close_white)
            binding.buttonAction.setOnClickListener { onClearClick(packageName) }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DownloadTaskUiModel>() {
            override fun areItemsTheSame(
                oldItem: DownloadTaskUiModel,
                newItem: DownloadTaskUiModel
            ): Boolean = oldItem.packageName == newItem.packageName

            override fun areContentsTheSame(
                oldItem: DownloadTaskUiModel,
                newItem: DownloadTaskUiModel
            ): Boolean = oldItem == newItem
        }
    }
}
