package android.example.app.ui.common

import android.example.app.R
import android.example.app.base.AdapterDelegate
import android.example.app.base.BaseViewHolder
import android.example.app.databinding.ItemErrorBinding
import android.example.domain.BaseModel
import android.view.ViewGroup

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