package com.example.myapplication.films

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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
import com.example.domain.usecase.GetAllFilmsFromDbUseCase
import com.example.domain.usecase.RemoveFilmFromFavouriteUseCase
import com.example.domain.usecase.SaveToFavouriteUseCase
import com.example.domain.usecase.UpdateSaveStateUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.base.MainActivity
import com.example.myapplication.databinding.FragmentFilmsListBinding
import com.example.myapplication.details.FilmsDetailsFragment
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
            SaveToFavouriteUseCase(repository),
            RemoveFilmFromFavouriteUseCase(repository),
            GetAllFilmsFromDbUseCase(repository),
            UpdateSaveStateUseCase(repository)
        )
    }

    private val filmsAdapter by lazy {
        FilmsAdapter(
            save = {
                viewModel.save(it)
            },
            delete = {
                viewModel.delete(it)
            },
            navigateToDetails = { id ->
                navigateToFilmsDetails(id)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentFilmsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            viewModel.fetchFilms()
            rvFilms.adapter = filmsAdapter
            var spanCount = 1
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.filmsListUiState.collect {
                        when (it) {
                            is FilmsListUiState.Idle -> {}
                            is FilmsListUiState.Empty -> {
                                spanCount = 1
                                filmsAdapter.submitItem(Empty())
                            }
                            is FilmsListUiState.Loading -> {
                                spanCount = 1
                                filmsAdapter.submitItem(Loader())
                            }
                            is FilmsListUiState.Error -> filmsAdapter.submitItem(
                                com.example.domain.model.Error(
                                    it.message
                                )
                            )
                            is FilmsListUiState.Success -> {
                                spanCount = 2
                                filmsAdapter.submitList(it.data.toMutableList())
                            }
                        }
                        rvFilms.layoutManager = GridLayoutManager(activity, spanCount)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.sorting_app_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sort_popular -> {
            // TODO implement
            true
        }
        R.id.sort_alphabet -> {
            // TODO implement
            true
        }
        R.id.sort_count -> {
            // TODO implement
            true
        }
        R.id.sort_rating -> {
            // TODO implement
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToFilmsDetails(id: String) {
        val fragment: Fragment = FilmsDetailsFragment()
        val arguments = Bundle()
        arguments.putString(FilmsDetailsFragment.FILM_ID, id)
        arguments.putBoolean(FilmsDetailsFragment.IS_SAVED, viewModel.findIsSavedFilmById(id))
        fragment.arguments = arguments
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_view, fragment)
            ?.addToBackStack(fragment.javaClass.simpleName)?.commit()
    }
}