package com.example.myapplication.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.model.Empty
import com.example.domain.model.Loader
import com.example.domain.usecase.ClearAllSavedUseCase
import com.example.domain.usecase.GetFavouriteFilmsListUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.base.MainActivity
import com.example.myapplication.databinding.FragmentFavouriteFilmsBinding
import com.example.myapplication.details.FilmsDetailsFragment
import com.example.myapplication.films.FilmsListUiState
import kotlinx.coroutines.launch

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteFilmsBinding

    private val viewModel: FavouriteViewModel by lazy {
        val app = requireActivity().application as App
        val repository = FilmRepositoryImpl(
            RemoteDataSourceImpl(app.apiService),
            DbDataSourceImpl(app.filmDao)
        )
        FavouriteViewModel(
            getFavouriteUseCase = GetFavouriteFilmsListUseCase(repository),
            removeFilmsFromDbUseCase = RemoveFilmFromFavouriteUseCase(repository),
            clearAllSavedFilmsUseCase = ClearAllSavedUseCase(repository)
        )
    }

    private val favouriteAdapter by lazy {
        FavoriteListAdapter(
            removeFromSaved = {
                //viewModel.removeFromSaved(it)
            },
            navigateToDetails = { id, isSaved ->
                navigateToFilmsDetails(id, isSaved)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteFilmsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchFilms()
        with(binding) {
            rvFavourite.adapter = favouriteAdapter
            var spanCount = 1
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.favouriteUiState.collect {
                        when (it) {
                            is FilmsListUiState.Idle -> {}
                            is FilmsListUiState.Empty -> {
                                spanCount = 1
                                favouriteAdapter.submitItem(Empty())
                            }
                            is FilmsListUiState.Loading -> {
                                spanCount = 1
                                favouriteAdapter.submitItem(Loader())
                            }
                            is FilmsListUiState.Error -> favouriteAdapter.submitItem(com.example.domain.model.Error(it.message))
                            is FilmsListUiState.Success -> {
                                spanCount = 2
                                favouriteAdapter.submitList(it.data.toMutableList())
                            }
                        }
                        rvFavourite.layoutManager = GridLayoutManager(activity, spanCount)
                    }
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        (activity as MainActivity).menuInflater.inflate(R.menu.app_bar_menu, menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.clear -> {
            viewModel.clearAll()// User chose the "Settings" item, show the app settings UI...
            true
        }
        R.id.search -> {
            // TODO implement
            // viewModel.search()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToFilmsDetails(id: String, isSaved: Boolean) {
        val fragment: Fragment = FilmsDetailsFragment()
        arguments?.putString(FilmsDetailsFragment.FILM_ID, id)
        arguments?.putBoolean(FilmsDetailsFragment.FILM_ID, isSaved)
        fragment.arguments = arguments
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.addToBackStack(fragment.javaClass.simpleName)?.commit()
    }
}