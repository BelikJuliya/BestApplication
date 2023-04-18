package com.example.myapplication.films

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.model.Empty
import com.example.domain.model.Loader
import com.example.domain.usecase.GetFilmsFromDbUseCase
import com.example.domain.usecase.SaveFilmsToDbUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentFilmsListBinding
import kotlinx.coroutines.launch

class FilmsListFragment : Fragment(R.layout.fragment_films_list) {

    private lateinit var binding: FragmentFilmsListBinding

    private val viewModel: FilmsListViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        FilmsListViewModel(
            SaveFilmsToDbUseCase(repository),
            GetFilmsFromDbUseCase(repository)
        )
    }

    private val adapter by lazy {
        FilmsAdapter(
            saveFilm = {
                viewModel.saveFilm(it)
            },
            removeFromSaved = {
                viewModel.removeFromSaved(it)
            },
//            downloadImage = {
//                viewModel.downLoadImage(it)
//            }
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
                        is FilmsListUiState.Idle -> {}
                        is FilmsListUiState.Empty -> adapter.submitItem(Empty())
                        is FilmsListUiState.Loading -> adapter.submitItem(Loader())
                        is FilmsListUiState.Error -> adapter.submitItem(com.example.domain.model.Error(it.message))
                        is FilmsListUiState.Success -> adapter.submitList(it.data.toMutableList())
                    }
                }
            }
        }
    }
}