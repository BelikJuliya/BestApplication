package com.example.myapplication.details

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
            LoadDetailsFilmUseCase(repository),
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
        val filmId = arguments?.getString(FILM_ID, "")
        filmId?.let { viewModel.fetchFilmDetails(requireActivity().resources.getString(R.string.api_key), it) }
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
                                tvTitle.text = it.data.title
                                tvDescription.text = it.data.plot
                                tvReleaseDate.text = it.data.releaseDate
                                actorsAdapter.submitList(it.data.actorList.toMutableList())
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

    companion object {

        const val FILM_ID = "filmId"
    }
}