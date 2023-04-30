package com.example.myapplication.favourite

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemFilmBinding

class FavouriteViewHolder(
    parent: ViewGroup,
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {},
    private val navigateToDetails: (id: String, isSaved: Boolean) -> Unit
) : BaseViewHolder(parent, R.layout.item_film) {

    private lateinit var binding: ItemFilmBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemFilmBinding.bind(itemView)
        with(binding) {
            model as FilmDomainModel
            tvTitle.text = model.title
            tvRating.text = model.rating
            Glide.with(binding.root).load(model.imageUrl).into(ivPreview)
            ivLike.setImageResource(R.drawable.ic_saved)
            ivLike.setOnClickListener {
                removeFromSaved(model)
            }
            root.setOnClickListener { navigateToDetails(model.id, model.isSaved) }
        }
    }

    private fun bindName(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            tvTitle.text = model.title
        }
    }

    private fun bindRating(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            tvRating.text = model.rating
        }
    }

    private fun bindImage(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            Glide.with(binding.root).load(model.imageUrl).into(ivPreview)
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
            }
        }
    }
}

class FavouriteFilmDelegate(
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {},
    private val navigateToDetails: (id: String, isSaved: Boolean) -> Unit
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        FavouriteViewHolder(
            parent,
            removeFromSaved,
            navigateToDetails
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is FilmDomainModel
}