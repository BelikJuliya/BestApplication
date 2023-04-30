package com.example.myapplication.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.favourite_app_bar_menu, menu)
        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView?
        searchView?.queryHint = resources.getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                favouriteAdapter.customFilter.filter(qString)
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                //menuItem.collapseActionView()
                if (!searchView.isIconified) {
                    searchView.isIconified = true
                }
                menuItem.collapseActionView()
                return false
                //return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.clear -> {
            viewModel.clearAll()
            true
        }
        R.id.search -> {
            val searchView = item.actionView as SearchView?
//            searchView.expandActionView()
            searchView?.isIconified = false
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