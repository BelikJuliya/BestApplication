package com.example.myapplication.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.usecase.LoadDetailsFilmUseCase
import com.example.domain.usecase.LoadYouTubeTrailerUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentFilmDetailsBinding
import kotlinx.coroutines.launch

class FilmsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFilmDetailsBinding

    private val actorsAdapter by lazy {
        ActorsAdapter()
    }

    private val viewModel: FragmentDetailsViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        FragmentDetailsViewModel(
            LoadDetailsFilmUseCase(repository)

        )
    }
    private val youTubeTrailerVm: LoadTrailerYouTubeViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        LoadTrailerYouTubeViewModel(
            LoadYouTubeTrailerUseCase(repository)
        )

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment//
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filmId = arguments?.getString(FILM_ID, "")
        filmId?.let {
            viewModel.fetchFilmDetails(
                requireActivity().resources.getString(R.string.api_key),
                it
            )
        }
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
                                // show error
                            }
                            is FilmDetailState.Success -> {
                                Glide.with(binding.root).load(it.data.image).into(ivPreview)
                                tvTitle.text = it.data.title
                                tvDescription.text = it.data.plot
                                tvReleaseDate.text = it.data.releaseDate
                                actorsAdapter.submitList(it.data.actorList.toMutableList())

                            }
                        }
                    }
                }

            }
        }
        binding.btnYoutube.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {


                youTubeTrailerVm.loadTrailer(
                    requireActivity().resources.getString(R.string.api_key),
                    filmId ?: return@launch
                )
                youTubeTrailerVm.trailerUiState.collect { state ->
                    when (state) {
                        is TrailerState.Success -> {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.data.videoUrl))
                            startActivity(intent)
                        }
                        is TrailerState.Error -> {
                            Toast.makeText(context, "Failed to load trailer", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is TrailerState.Loading -> Toast.makeText(
                            context,
                            "Loading",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    companion object {
        const val FILM_ID = "filmId"
    }
}
