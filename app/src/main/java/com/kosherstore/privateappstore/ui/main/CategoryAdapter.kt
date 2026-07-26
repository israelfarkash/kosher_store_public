package com.kosherstore.privateappstore.ui.main

import android.content.res.ColorStateList
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
            
            cardCategory.setCardBackgroundColor(data.bgColor)
            layoutIconBg.backgroundTintList = ColorStateList.valueOf(data.iconBgColor)
            imageCategoryIcon.imageTintList = ColorStateList.valueOf(data.iconTint)

            root.setOnClickListener { onCategoryClicked(data.name) }
        }
    }
}

data class CategoryDisplayData(
    val name: String,
    val iconRes: Int,
    val bgColor: Int,
    val iconBgColor: Int,
    val iconTint: Int
) {
    companion object {
        fun mapFromNames(names: List<String>): List<CategoryDisplayData> {
            return names.map { name ->
                val (icon, bg, iconBg, tint) = when {
                    name.contains("מוזיקה", true) || name.contains("Music", true) -> 
                        Quad(R.drawable.ic_category_music, 0xFFF3E8FF.toInt(), 0xFFE9D5FF.toInt(), 0xFF7E22CE.toInt())
                    name.contains("ניווט", true) || name.contains("Navigation", true) || name.contains("Waze", true) -> 
                        Quad(R.drawable.ic_category_navigation, 0xFFCCFBF1.toInt(), 0xFF99F6E4.toInt(), 0xFF0F766E.toInt())
                    name.contains("פיננסים", true) || name.contains("Finance", true) -> 
                        Quad(R.drawable.ic_category_finance, 0xFFE0F2FE.toInt(), 0xFFBAE6FD.toInt(), 0xFF0369A1.toInt())
                    name.contains("תחבורה", true) || name.contains("Transport", true) -> 
                        Quad(R.drawable.ic_category_transport, 0xFFE0F2FE.toInt(), 0xFFBAE6FD.toInt(), 0xFF0891B2.toInt())
                    name.contains("גוגל", true) || name.contains("Google", true) -> 
                        Quad(R.drawable.ic_category_google, 0xFFFEE2E2.toInt(), 0xFFFECACA.toInt(), 0xFFB91C1C.toInt())
                    name.contains("מסרים", true) || name.contains("Messaging", true) -> 
                        Quad(R.drawable.ic_category_messaging, 0xFFD1FAE5.toInt(), 0xFFA7F3D0.toInt(), 0xFF047857.toInt())
                    name.contains("כלים", true) || name.contains("Tools", true) -> 
                        Quad(R.drawable.ic_category_tools, 0xFFFEF3C7.toInt(), 0xFFFDE68A.toInt(), 0xFFB45309.toInt())
                    name.contains("מדיה", true) || name.contains("Media", true) -> 
                        Quad(R.drawable.ic_category_media, 0xFFFFE4E6.toInt(), 0xFFFECDD3.toInt(), 0xFFBE123C.toInt())
                    name.contains("קניות", true) || name.contains("Shopping", true) -> 
                        Quad(R.drawable.ic_category_shopping, 0xFFFFEDD5.toInt(), 0xFFFED7AA.toInt(), 0xFFC2410C.toInt())
                    name.contains("לימוד", true) || name.contains("Education", true) -> 
                        Quad(R.drawable.ic_category_education, 0xFFE0E7FF.toInt(), 0xFFC7D2FE.toInt(), 0xFF4338CA.toInt())
                    else -> 
                        Quad(R.drawable.ic_category_tools, 0xFFF3F4F6.toInt(), 0xFFE5E7EB.toInt(), 0xFF374151.toInt())
                }
                CategoryDisplayData(name, icon, bg, iconBg, tint)
            }
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
