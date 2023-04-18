package com.example.myapplication.details

import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.ActorDomainModel
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemActorBinding


class ActorViewHolder(
    parent: ViewGroup,
//    private val downloadImage: (url: String) -> Bitmap?
) : BaseViewHolder(parent, R.layout.item_actor) {

    private lateinit var binding: ItemActorBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemActorBinding.bind(itemView)
        with(binding) {
            model as ActorDomainModel
            tvActorName.text = model.name
            tvCharacterName.text = model.asCharacter

//            ivPreview.setImageBitmap(downloadImage(model.imageUrl))

        }
    }

    private fun bindName(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
            tvActorName.text = model.name
        }
    }

    private fun bindAsCharacter(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
            tvCharacterName.text = model.asCharacter
        }
    }

    private fun bindImage(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
//            ivPreview.setImageBitmap(downloadImage(model.imageUrl))
        }
    }

    override fun bindPayload(
        model: BaseModel,
        viewHolder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {
        payloads.forEach {
            when (it) {
                BaseModelPayload.ACTOR_NAME -> bindName(model)
                BaseModelPayload.IMAGE -> bindImage(model)
                BaseModelPayload.AS_CHARACTER -> bindAsCharacter(model)
            }
        }
    }
}

class ActorDelegate(
//    private val downloadImage: (url: String) -> Bitmap?

) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        ActorViewHolder(
            parent,
//            downloadImage
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is FilmDomainModel
}