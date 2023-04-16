package splash_screen_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.data.remote.FilmRepositoryImpl
import com.example.domain.usecase.LoadFilmsFromRemoteUseCase
import com.example.myapplication.R
import com.example.myapplication.base.App
import com.example.myapplication.databinding.FragmentSplashBinding
import com.example.myapplication.films_list_flow.FilmsListViewModel

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SplashScreenViewModel by lazy {
        SplashScreenViewModel(
            LoadFilmsFromRemoteUseCase(
                FilmRepositoryImpl((requireActivity().application as App).apiService)
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
        viewModel.fetchFilms("k_12345678")

    }
}