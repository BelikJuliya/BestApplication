package com.example.myapplication.base

import com.example.domain.model.Loader
import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemLoaderBinding

class LoaderViewHolder(
    val parent: ViewGroup,
) : BaseViewHolder(parent, R.layout.item_loader) {

    private lateinit var binding: ItemLoaderBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemLoaderBinding.bind(itemView)
        with(binding) {
            tvWait.text = root.resources.getString(R.string.wait)
        }
    }
}

class LoaderDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        LoaderViewHolder(
            parent,
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is Loader
}