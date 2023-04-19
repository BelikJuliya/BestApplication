package com.example.myapplication.details

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.ActorDomainModel
import com.example.domain.model.FilmDetailsDomainModel
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemActorBinding


class ActorViewHolder(
    parent: ViewGroup,
) : BaseViewHolder(parent, R.layout.item_actor) {

    private lateinit var binding: ItemActorBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemActorBinding.bind(itemView)
        with(binding) {
            model as ActorDomainModel
            tvActorName.text = model.name
            tvRole.text = model.asCharacter

        }
    }

    private fun bindName(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
            tvActorName.text = model.name
        }
    }

    private fun bindRole(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
            tvRole.text = model.asCharacter
        }
    }

    private fun bindImage(model: BaseModel) {
        model as ActorDomainModel
        with(binding) {
            Glide.with(binding.root).load(model.image).into(ivActorPhoto)
        }
    }

    override fun bindPayload(
        model: BaseModel,
        viewHolder: BaseViewHolder,
        payloads: MutableList<Any>
    ) {
        payloads.forEach {
            when (it) {
                BaseModelPayload.NAME -> bindName(model)
                BaseModelPayload.IMAGE -> bindImage(model)
                BaseModelPayload.ROLE -> bindRole(model)
            }
        }
    }
}

class ActorDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        ActorViewHolder(
            parent,
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is FilmDomainModel
}