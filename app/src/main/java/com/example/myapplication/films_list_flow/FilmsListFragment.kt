package com.example.myapplication.films_list_flow

import com.example.domain.model.Empty
import com.example.domain.model.Loader
import com.example.domain.model.Error
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.remote.FilmRepositoryImpl
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentFilmsListBinding
import kotlinx.coroutines.launch

class FilmsListFragment : Fragment(R.layout.fragment_films_list) {

    private lateinit var binding: FragmentFilmsListBinding

    private val viewModel: FilmsListViewModel =
        FilmsListViewModel(LoadFilmsFromRemoteUseCase(FilmRepositoryImpl((activity?.application as App).apiService)))
    private val adapter by lazy {
        FilmsAdapter(
            saveFilm = { viewModel.saveFilm(it) },
            removeFromSaved = { viewModel.removeFromSaved(it) },
            downloadImage = { viewModel.downLoadImage(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmsListUiState.collect {
                    when (it) {
                        is FilmsListUiState.Empty -> adapter.submitItem(Empty())
                        is FilmsListUiState.Loading -> adapter.submitItem(Loader())
                        is FilmsListUiState.Error -> adapter.submitItem(Error(it.message))
                        is FilmsListUiState.Success -> adapter.submitList(it.data.toMutableList())
                    }
                }
            }
        }
    }
}