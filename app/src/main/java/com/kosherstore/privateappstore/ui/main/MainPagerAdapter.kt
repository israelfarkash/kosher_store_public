package com.kosherstore.privateappstore.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.card.MaterialCardView
import com.kosherstore.privateappstore.R

class MainPagerAdapter(
    private val onBindApps: (RecyclerView, ShimmerFrameLayout, View, MaterialCardView, TextView) -> Unit,
    private val onBindCategories: (RecyclerView) -> Unit
) : RecyclerView.Adapter<MainPagerAdapter.PagerViewHolder>() {

    override fun getItemCount(): Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val layout = if (viewType == 0) R.layout.layout_page_apps else R.layout.layout_page_categories
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int = position

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            if (position == 0) {
                val recycler = itemView.findViewById<RecyclerView>(R.id.recyclerApps)
                val shimmer = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmerLayout)
                val empty = itemView.findViewById<View>(R.id.layoutEmpty)
                val syncCard = itemView.findViewById<MaterialCardView>(R.id.cardSyncMessage)
                val syncText = itemView.findViewById<TextView>(R.id.textSyncMessage)
                onBindApps(recycler, shimmer, empty, syncCard, syncText)
            } else {
                val recycler = itemView.findViewById<RecyclerView>(R.id.recyclerCategories)
                onBindCategories(recycler)
            }
        }
    }
}
