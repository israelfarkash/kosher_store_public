package com.kosherstore.privateappstore.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.kosherstore.privateappstore.databinding.ItemScreenshotBinding
import com.kosherstore.privateappstore.ui.imageviewer.ImageViewerActivity

class ScreenshotAdapter : RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder>() {

    private var screenshots: List<String> = emptyList()

    fun submitList(urls: List<String>) {
        screenshots = urls
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        val binding = ItemScreenshotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScreenshotViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        holder.bind(screenshots[position], screenshots, position)
    }

    override fun getItemCount(): Int = screenshots.size

    class ScreenshotViewHolder(
        private val binding: ItemScreenshotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String, allUrls: List<String>, position: Int) {
            binding.imageScreenshot.load(url) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
            }

            binding.root.setOnClickListener {
                ImageViewerActivity.start(it.context, allUrls, position)
            }
        }
    }
}
