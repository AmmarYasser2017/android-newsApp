package com.example.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.LeftSidedCategoryBinding
import com.example.newsapp.databinding.RightSidedCategoryBinding
import com.example.newsapp.model.Category

class CategoriesAdapter(val categories: List<Category>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LEFT_SIDED_VIEW_TYPE) {
            var leftSidedBinding: LeftSidedCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.left_sided_category, parent, false
            )
            return ViewHolderLeft(leftSidedBinding)
        } else {
            var rightSidedBinding: RightSidedCategoryBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.right_sided_category, parent, false
            )
            return ViewHolderRight(rightSidedBinding)
        }

    }

    val LEFT_SIDED_VIEW_TYPE = 10
    val RIGHT_SIDED_VIEW_TYPE = 20
    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) LEFT_SIDED_VIEW_TYPE else RIGHT_SIDED_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = categories[position]

        if (getItemViewType(position) == LEFT_SIDED_VIEW_TYPE) {
            (holder as ViewHolderLeft).bind(item)
        } else {
            (holder as ViewHolderRight).bind(item)
        }


        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(pos: Int, category: Category)
    }

    class ViewHolderLeft(val view: LeftSidedCategoryBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(model: Category) {
            view.model = model
            view.invalidateAll()
        }
    }

    class ViewHolderRight(val view: RightSidedCategoryBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(model: Category) {
            view.model = model
            view.invalidateAll()
        }
    }
}