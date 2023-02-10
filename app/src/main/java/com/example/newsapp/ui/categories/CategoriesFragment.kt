package com.example.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentCategoriesBinding
import com.example.newsapp.model.Category

class CategoriesFragment : Fragment() {

    val categoriesList = listOf(
        Category(
            "sports", R.drawable.sports, R.string.sports, R.color.sports
        ),
        Category(
            "technology", R.drawable.politics, R.string.technology, R.color.politics
        ),
        Category(
            "health", R.drawable.health, R.string.health, R.color.health
        ),
        Category(
            "business", R.drawable.business, R.string.business, R.color.business
        ),
        Category(
            "general", R.drawable.environment, R.string.general, R.color.environment
        ),
        Category(
            "science", R.drawable.science, R.string.science, R.color.science
        ),
    )
    val adapter = CategoriesAdapter(categoriesList)

    lateinit var fragmentCategoriesBinding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_categories, container, false
        )
        return fragmentCategoriesBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentCategoriesBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener = object : CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, category: Category) {
                onCategoryClickListener?.onCategoryClick(category)
            }
        }
    }

    var onCategoryClickListener: OnCategoryClickListener? = null

    interface OnCategoryClickListener {
        fun onCategoryClick(category: Category)
    }
}