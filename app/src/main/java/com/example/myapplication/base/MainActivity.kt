package com.example.myapplication.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.favourite.FavouriteFragment
import com.example.myapplication.films.FilmsListFragment
import com.example.myapplication.splash.SplashFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, SplashFragment())
                .commit()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.popular -> {
                    navigateToFilmsList()
                    true
                }
                R.id.saved -> {
                    navigateToFavouriteList()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToFilmsList() {
        val fragment: Fragment = FilmsListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    private fun navigateToFavouriteList() {
        val fragment: Fragment = FavouriteFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }
}
