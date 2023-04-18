package com.example.myapplication.films_details_flow

import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.ActorDomainModel
import com.example.domain.model.FilmDetailsScreenDomainModel
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemActorBinding
import com.example.myapplication.databinding.ItemFilmBinding


class ActorDelegate (
    parent: ViewGroup,
    private val saveFilm: (actor: ActorDomainModel) -> Unit = {},
    private val removeFromSaved: (actor: ActorDomainModel) -> Unit = {},
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

    private fun bindRating(model: BaseModel) {
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
                BaseModelPayload.TITLE -> bindName(model)
                BaseModelPayload.IMAGE -> bindImage(model)
                BaseModelPayload.RATING -> bindRating(model)
                BaseModelPayload.SAVED_STATE -> bindSavedState(model)
            }
        }
    }
}

class FilmsDelegate(
    private val saveCurrency: (film: FilmDomainModel) -> Unit = {},
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {},
//    private val downloadImage: (url: String) -> Bitmap?

) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        FilmsViewHolder(
            parent,
            saveCurrency,
            removeFromSaved,
//            downloadImage
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is FilmDomainModel
}