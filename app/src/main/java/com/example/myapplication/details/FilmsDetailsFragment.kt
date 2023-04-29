package com.example.myapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.usecase.DetailsFilmUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import com.example.domain.usecase.UpdateSaveStateUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentFilmDetailsBinding
import kotlinx.coroutines.launch

class FilmsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFilmDetailsBinding

    private val actorsAdapter by lazy {
        ActorsAdapter()
    }

    private val viewModel: DetailsViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        DetailsViewModel(
            DetailsFilmUseCase(repository),
            SaveToFavouriteUseCase(repository),
            RemoveFilmFromFavouriteUseCase(repository),
            UpdateSaveStateUseCase(repository)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.filmId = arguments?.getString(FILM_ID, "") ?: ""
        viewModel.isSaved = arguments?.getBoolean(IS_SAVED, false) ?: false

        viewModel.fetchFilmDetails(requireActivity().resources.getString(R.string.api_key))

        with(binding) {
            rvActors.adapter = actorsAdapter
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.detailsUiState.collect {
                        when (it) {
                            is FilmDetailState.Loading -> {
                                // show loader
                            }
                            is FilmDetailState.Error -> {
                                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                            }
                            is FilmDetailState.Success -> {
                                Glide.with(binding.root).load(it.data.image).into(ivPreview)
                                val detailState = it.data
                                with(detailState) {
                                    tvTitle.text = title
                                    tvDescription.text = plot
                                    tvReleaseDate.text = releaseDate
                                    actorsAdapter.submitList(actorList.toMutableList())
                                    if (isSaved) {
                                        ivAddToFavourite.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                requireActivity(),
                                                R.drawable.ic_saved
                                            )
                                        )
                                    } else {
                                        ivAddToFavourite.setImageDrawable(
                                            ContextCompat.getDrawable(
                                                requireActivity(),
                                                R.drawable.ic_unsaved
                                            )
                                        )
                                    }
                                    ivAddToFavourite.setOnClickListener {
                                        if (isSaved) {
                                            viewModel.deleteFromFavourite()
                                        } else {
                                            viewModel.saveToFavourite()
                                        }
                                    }
                                    btnYoutube.setOnClickListener { _ ->
                                        viewModel.goToYouTube(it.data.id)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        const val FILM_ID = "filmId"
        const val IS_SAVED = "isSaved"
    }
}