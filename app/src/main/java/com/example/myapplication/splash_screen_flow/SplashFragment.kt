package com.example.myapplication.splash_screen_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.remote.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.IRemoteDataSource
import com.example.domain.model.Empty
import com.example.domain.model.Error
import com.example.domain.model.Loader
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.films_list_flow.FilmsListViewModel
import com.example.myapplication.splash_screen_flow.SplashScreenViewModel
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashScreenViewModel by lazy {
        val app = requireActivity().application as App
        SplashScreenViewModel(
            LoadFilmsFromRemoteUseCase(
                FilmRepositoryImpl(
                    RemoteDataSourceImpl(app.apiService),
                    DbDataSourceImpl(app.filmDao)
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.splashImage.setImageResource(R.drawable.splashcat)
        // Здесь можно добавить другие элементы для Splash Screen

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fetchFilms(resources.getString(R.string.api_key))
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filmsListUiState.collect {
//                    when (it) {
//                        is FilmsListUiState.Empty -> adapter.submitItem(Empty())
//                        is FilmsListUiState.Loading -> adapter.submitItem(Loader())
//                        is FilmsListUiState.Error -> adapter.submitItem(Error(it.message))
//                        is FilmsListUiState.Success -> adapter.submitList(it.data.toMutableList())
//                    }
                }
            }
        }
    }
}