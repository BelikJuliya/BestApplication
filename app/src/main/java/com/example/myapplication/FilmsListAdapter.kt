package com.example.myapplication

import android.graphics.Bitmap
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.domain.model.FilmDomainModel
import com.example.myapplication.databinding.ItemFilmBinding

class FilmsListAdapter(
    private val saveFilm: (film: FilmDomainModel) -> Unit,
    private val removeFromSaved: (film: FilmDomainModel) -> Unit,
    private val downloadImage: (url: String) -> Bitmap?
) : ListAdapter<FilmDomainModel, RecyclerView.ViewHolder>(ItemCallback) {

    object ItemCallback : DiffUtil.ItemCallback<FilmDomainModel>() {

        override fun areItemsTheSame(
            oldItem: FilmDomainModel,
            newItem: FilmDomainModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FilmDomainModel, newItem: FilmDomainModel): Boolean {
            if (oldItem.id != newItem.id) return false
            if (oldItem.title != newItem.title) return false
            if (oldItem.imageUrl != newItem.imageUrl) return false
            if (oldItem.iMDbRating != newItem.iMDbRating) return false
            if (oldItem.isSaved != newItem.isSaved) return false
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RecordViewHolder(
            ItemFilmBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as RecordViewHolder
        holder.bind(getItem(position) as FilmDomainModel)
    }

    inner class RecordViewHolder(binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var binding: ItemFilmBinding

        fun bind(model: FilmDomainModel) {
            binding = ItemFilmBinding.bind(itemView)
            with(binding) {
                tvTitle.text = model.title
                tvRating.text = model.iMDbRating

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
                // TODO load image an set it resource
            }
        }
    }
}