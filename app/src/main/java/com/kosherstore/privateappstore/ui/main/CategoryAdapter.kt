package com.kosherstore.privateappstore.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.ItemCategorySquareBinding

class CategoryAdapter(
    private val onCategoryClicked: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var items: List<CategoryDisplayData> = emptyList()

    fun submitList(newItems: List<CategoryDisplayData>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategorySquareBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class CategoryViewHolder(
        private val binding: ItemCategorySquareBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CategoryDisplayData) = with(binding) {
            textCategoryName.text = data.name
            imageCategoryIcon.setImageResource(data.iconRes)
            root.setOnClickListener { onCategoryClicked(data.name) }
        }
    }
}

data class CategoryDisplayData(
    val name: String,
    val iconRes: Int
) {
    companion object {
        fun mapFromNames(names: List<String>): List<CategoryDisplayData> {
            return names.map { name ->
                val icon = when {
                    name.contains("פיננסים", true) || name.contains("Finance", true) -> R.drawable.ic_category_finance
                    name.contains("תחבורה", true) || name.contains("Transport", true) -> R.drawable.ic_category_transport
                    name.contains("גוגל", true) || name.contains("Google", true) -> R.drawable.ic_category_google
                    name.contains("מסרים", true) || name.contains("Messaging", true) -> R.drawable.ic_category_messaging
                    name.contains("כלים", true) || name.contains("Tools", true) -> R.drawable.ic_category_tools
                    name.contains("מדיה", true) || name.contains("Media", true) -> R.drawable.ic_category_media
                    name.contains("קניות", true) || name.contains("Shopping", true) -> R.drawable.ic_category_shopping
                    name.contains("לימוד", true) || name.contains("Education", true) -> R.drawable.ic_category_education
                    name.contains("כללי", true) || name.contains("General", true) -> R.drawable.ic_category_tools
                    else -> R.drawable.ic_category_tools
                }
                CategoryDisplayData(name, icon)
            }
        }
    }
}
