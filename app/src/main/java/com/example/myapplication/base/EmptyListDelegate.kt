package android.example.app.ui.common

import android.example.app.R
import android.example.app.base.AdapterDelegate
import android.example.app.base.BaseViewHolder
import android.example.app.databinding.ItemEmptyBinding
import android.example.domain.BaseModel
import com.example.domain.model.Empty
import android.view.ViewGroup

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