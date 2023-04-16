package com.example.myapplication.base

import com.example.domain.model.Empty
import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemEmptyBinding

class EmptyCurrencyListViewHolder(
    val parent: ViewGroup,
) : BaseViewHolder(parent, R.layout.item_empty) {

    private lateinit var binding: ItemEmptyBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemEmptyBinding.bind(itemView)
    }
}

class EmptyListDelegate(
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        EmptyCurrencyListViewHolder(
            parent
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is Empty
}