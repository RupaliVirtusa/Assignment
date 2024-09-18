package com.assignment.codingassignment.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.platform.LocalContext
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.codingassignment.R
import com.assignment.codingassignment.databinding.ListItemBinding
import com.assignment.codingassignment.network.model.RecipeDto
import com.bumptech.glide.Glide

class RecipeAdapter(var receipeList: List<RecipeDto>) :
    RecyclerView.Adapter<RecipeAdapter.ReceipeHolder>() {

    var inflater: LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReceipeHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        val itemBinding: ListItemBinding =
            DataBindingUtil.inflate(inflater!!, R.layout.list_item, parent, false)

        return ReceipeHolder(itemBinding, parent.context)
    }

    override fun getItemCount(): Int {
        return receipeList.size
    }

    override fun onBindViewHolder(holder: ReceipeHolder, position: Int) {
        holder.bind(receipeList[position])
    }

    fun updateReceipeList(list: List<RecipeDto>) {
        receipeList = list
        notifyDataSetChanged()
    }

    class ReceipeHolder(itemView: ListItemBinding, context: Context) :
        RecyclerView.ViewHolder(itemView.root) {
        var receipeView: ListItemBinding
        var mContext = context

        init {
            receipeView = itemView
        }

        companion object {
            @JvmStatic
            @BindingAdapter("featuredImage")
            fun loadImage(imageView: ImageView, url: String) {
                Glide.with(imageView.context)
                    .load(url)
                    .into(imageView)
            }
        }

        fun bind(receipe: RecipeDto) {
            receipeView.receipe = receipe
        }
    }
}