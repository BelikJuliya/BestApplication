package com.example.myapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.data.FilmRepositoryImpl
import com.example.data.db.DbDataSourceImpl
import com.example.data.remote.RemoteDataSourceImpl
import com.example.domain.IRepository
import com.example.domain.usecase.GetFilmsFromDbUseCase
import com.example.domain.usecase.LoadDetailsFilmUseCase
import com.example.domain.usecase.SaveFilmsToDbUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentFilmsDetailsBinding
import com.example.myapplication.films.FilmsListViewModel


class FilmsDetailsFragment : Fragment() {
    private lateinit var  binding : FragmentFilmsDetailsBinding
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
    private lateinit var adapter: FilmDetailsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment//
        binding = FragmentFilmsDetailsBinding.inflate(inflater,container,false)
                return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actorRecyclerView.adapter = adapter
        viewModel.fetchFilmDetails(apiKey  =  resources.getString(R.string.api_key),)

    }
}