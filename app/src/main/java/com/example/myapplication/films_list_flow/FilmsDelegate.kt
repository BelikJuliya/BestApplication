package com.example.myapplication.films_list_flow

import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemFilmBinding

class FilmsViewHolder(
    val parent: ViewGroup,
    private val saveFilm: (film: FilmDomainModel) -> Unit = {},
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {}
) : BaseViewHolder(parent, R.layout.item_film) {

    private lateinit var binding: ItemFilmBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemFilmBinding.bind(itemView)
        with(binding) {
            model as FilmDomainModel
            bindName(model)
            bindRate(model)
            bindSavedState(model)
            root.setOnClickListener {
                if (model.isSaved) {
                    removeFromSaved(model)
                } else {
                    saveFilm(model)
                }
            }
        }
    }

    private fun bindName(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            tvTitle.text = model.title
        }
    }

    private fun bindRate(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            tvRating.text = model.iMDbRating
        }
    }

    private fun bindImage(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            // TODO load image
        }
    }


    private fun bindSavedState(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            if (model.isSaved) {
                ivLike.setImageResource(R.drawable.ic_saved)
            } else {
                ivLike.setImageResource(R.drawable.ic_unsaved)
            }
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
                BaseModelPayload.IMAGE -> bindRate(model)
                BaseModelPayload.RATING -> bindSavedState(model)
            }
        }
    }
}

class FilmsDelegate(
    private val saveCurrency: (film: FilmDomainModel) -> Unit = {},
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {}
) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): BaseViewHolder =
        FilmsViewHolder(
            parent,
            saveCurrency,
            removeFromSaved
        )

    override fun isValidType(baseModel: BaseModel): Boolean = baseModel is FilmDomainModel
}