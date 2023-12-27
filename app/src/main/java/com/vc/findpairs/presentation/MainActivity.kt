package com.vc.findpairs.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.vc.findpairs.R
import com.vc.findpairs.databinding.ActivityMainBinding
import com.vc.findpairs.presentation.endgamepopup.EndGameFragment
import com.vc.findpairs.presentation.gamescreen.GameFragment
import com.vc.findpairs.presentation.menuscreen.MenuFragment
import com.vc.findpairs.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            viewModel.onEvent(event = Navigation.OnMenuFragment)
        }
        collectNavigation()
    }

    private fun collectNavigation() {
        collectLatestLifecycleFlow(viewModel.navigationState) { navigation ->
            when (navigation) {
                Navigation.OnEndGameFragment -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<EndGameFragment>(R.id.fragment_container)
                    }
                }

                Navigation.OnGameFragment -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<GameFragment>(R.id.fragment_container)
                    }
                }

                Navigation.OnMenuFragment -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<MenuFragment>(R.id.fragment_container)
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}