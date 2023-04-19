package com.example.myapplication.base

import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.domain.model.Error
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemErrorBinding

class ErrorViewHolder(
    val parent: ViewGroup,
    private val reload: () -> Unit = {}
) : BaseViewHolder(parent, R.layout.item_error) {

    private lateinit var binding: ItemErrorBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemErrorBinding.bind(itemView)
        with(binding) {
            model as com.example.domain.model.Error
            if (model.message.isNotEmpty()) {
                tvError.text = model.message
            }
        }
    }
}

class ErrorDelegate(
    private val reload: () -> Unit = {}

) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        ErrorViewHolder(
            parent,
            reload
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is Error
}