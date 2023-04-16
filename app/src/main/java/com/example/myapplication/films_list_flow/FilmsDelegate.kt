package com.example.myapplication.films_list_flow

import android.graphics.Bitmap
import android.view.ViewGroup
import com.example.domain.BaseModel
import com.example.domain.BaseModelPayload
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.R
import com.example.myapplication.base.AdapterDelegate
import com.example.myapplication.base.BaseViewHolder
import com.example.myapplication.databinding.ItemFilmBinding

class FilmsViewHolder(
    parent: ViewGroup,
    private val saveFilm: (film: FilmDomainModel) -> Unit = {},
    private val removeFromSaved: (film: FilmDomainModel) -> Unit = {},
//    private val downloadImage: (url: String) -> Bitmap?
) : BaseViewHolder(parent, R.layout.item_film) {

    private lateinit var binding: ItemFilmBinding

    override fun bind(model: BaseModel, viewHolder: BaseViewHolder) {
        binding = ItemFilmBinding.bind(itemView)
        with(binding) {
            model as FilmDomainModel
            tvTitle.text = model.title
            tvRating.text = model.rating
//            ivPreview.setImageBitmap(downloadImage(model.imageUrl))

            if (model.isSaved) {
                ivLike.setImageResource(R.drawable.ic_saved)
            } else {
                ivLike.setImageResource(R.drawable.ic_unsaved)
            }
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

    private fun bindRating(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
            tvRating.text = model.rating
        }
    }

    private fun bindImage(model: BaseModel) {
        model as FilmDomainModel
        with(binding) {
//            ivPreview.setImageBitmap(downloadImage(model.imageUrl))
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