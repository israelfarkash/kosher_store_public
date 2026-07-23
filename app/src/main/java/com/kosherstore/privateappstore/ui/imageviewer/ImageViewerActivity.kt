package com.kosherstore.privateappstore.ui.imageviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.size.Size
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.ActivityImageViewerBinding
import com.kosherstore.privateappstore.databinding.ItemFullscreenZoomPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageViewerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.btnClose.updateLayoutMargins(top = dp(16) + bars.top)
            binding.textPageIndicator.updateLayoutMargins(bottom = dp(20) + bars.bottom)
            insets
        }

        val urls = resolveImageUrls(intent)
        val startIndex = intent.getIntExtra(EXTRA_INITIAL_INDEX, 0)
            .coerceIn(0, (urls.size - 1).coerceAtLeast(0))
        if (urls.isEmpty()) {
            finish()
            return
        }

        binding.viewPager.adapter = ZoomScreenshotPagerAdapter(urls)
        binding.viewPager.setCurrentItem(startIndex, false)

        binding.btnClose.setOnClickListener { finish() }

        bindPageIndicator(urls.size, startIndex.coerceAtLeast(0))
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bindPageIndicator(urls.size, position)
            }
        })
    }

    private fun bindPageIndicator(total: Int, index: Int) {
        binding.textPageIndicator.apply {
            if (total <= 1) {
                visibility = View.GONE
                return
            }
            visibility = View.VISIBLE
            text = getString(R.string.image_viewer_page, index + 1, total)
        }
    }

    private fun View.updateLayoutMargins(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
        val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        if (left != null) lp.leftMargin = left
        if (top != null) lp.topMargin = top
        if (right != null) lp.rightMargin = right
        if (bottom != null) lp.bottomMargin = bottom
        layoutParams = lp
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun resolveImageUrls(intent: Intent): List<String> {
        val bulk = intent.getStringArrayListExtra(EXTRA_IMAGE_URLS)
            ?.filterNot { it.isBlank() }
            ?.distinct()
            .orEmpty()
        if (bulk.isNotEmpty()) return bulk
        return intent.getStringExtra(EXTRA_IMAGE_URL)
            ?.takeIf { it.isNotBlank() }
            ?.let { listOf(it) }.orEmpty()
    }

    private class ZoomScreenshotPagerAdapter(
        private val urls: List<String>
    ) : RecyclerView.Adapter<ZoomScreenshotPagerAdapter.VH>() {

        override fun getItemCount(): Int = urls.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            val inflate = LayoutInflater.from(parent.context)
            val itemBinding = ItemFullscreenZoomPageBinding.inflate(inflate, parent, false)
            return VH(itemBinding)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(urls[position])
        }

        class VH(private val binding: ItemFullscreenZoomPageBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(url: String) {
                binding.zoomImage.load(url) {
                    crossfade(true)
                    size(Size.ORIGINAL)
                    listener(
                        onSuccess = { _, _ ->
                            binding.zoomImage.setMinimumScale(1f)
                            binding.zoomImage.setMediumScale(2.5f)
                            binding.zoomImage.setMaximumScale(5f)
                            binding.zoomImage.setScale(1f, false)
                        }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_IMAGE_URL = "image_url"

        /** Multiple URLs for swipe-through gallery mode. */
        const val EXTRA_IMAGE_URLS = "image_urls"

        /** Starting position when [EXTRA_IMAGE_URLS] has more than one item. */
        const val EXTRA_INITIAL_INDEX = "initial_index"

        fun start(context: Context, urls: List<String>, startIndex: Int = 0) {
            val cleaned = urls.filterNot { it.isBlank() }
            if (cleaned.isEmpty()) return
            val intent = Intent(context, ImageViewerActivity::class.java).apply {
                putStringArrayListExtra(EXTRA_IMAGE_URLS, ArrayList(cleaned))
                putExtra(EXTRA_INITIAL_INDEX, startIndex.coerceIn(0, cleaned.size - 1))
            }
            context.startActivity(intent)
        }
    }
}
