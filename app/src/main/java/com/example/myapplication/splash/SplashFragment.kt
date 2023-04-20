package com.example.myapplication.splash

import SplashScreenViewModel
import com.example.myapplication.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.domain.usecase.SaveFilmsListUseCase
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.films.FilmsListFragment
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashScreenViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        SplashScreenViewModel(
            LoadFilmsFromRemoteUseCase(repository),
            SaveFilmsListUseCase(repository)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.splashImage.setImageResource(R.drawable.splashcat)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchFilms(requireActivity().resources.getString(R.string.api_key)).collect {
                    navigateToFilmsList()
                }
            }
        }
    }

    private fun navigateToFilmsList() {
        val fragment: Fragment = FilmsListFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.addToBackStack(fragment.javaClass.simpleName)?.commit()
    }
}