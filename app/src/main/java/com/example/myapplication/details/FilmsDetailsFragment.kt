package com.example.myapplication.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentFilmsDetailsBinding


class FilmsDetailsFragment : Fragment() {
    private lateinit var  binding : FragmentFilmsDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment//
        binding = FragmentFilmsDetailsBinding.inflate(inflater,container,false)
                return binding.root

    }
}