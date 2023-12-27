package com.vc.findpairs.presentation.endgamepopup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.vc.findpairs.databinding.FragmentEndGameBinding
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.presentation.MainViewModel
import com.vc.findpairs.presentation.Navigation
import com.vc.findpairs.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EndGameFragment : Fragment() {

    private var _binding: FragmentEndGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EndGameViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var coin: Coin

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEndGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onEvent(event = EndGameEvent.GetEarnedMoney)
        with(binding) {
            doubleReward.setOnClickListener {
                doubleReward.isEnabled = false
                viewModel.onEvent(event = EndGameEvent.DoubleReward(coin = coin))
            }
            homeButton.setOnClickListener {
                mainViewModel.onEvent(event = Navigation.OnMenuFragment)
            }
            nextLevelButton.setOnClickListener {
                viewModel.onEvent(event = EndGameEvent.NextLevel)
            }
        }
        collectEarnedMoney()
        collectEndGameState()
    }

    private fun collectEndGameState() {
        collectLatestLifecycleFlow(viewModel.endGameState) { endGame ->
            when (endGame) {
                EndGame.GoToGameFragment -> {
                    mainViewModel.onEvent(event = Navigation.OnGameFragment)
                }

                EndGame.GoToMenuFragment -> {
                    mainViewModel.onEvent(event = Navigation.OnMenuFragment)
                }

                EndGame.Default -> {}

            }
        }
    }

    private fun collectEarnedMoney() {
        collectLatestLifecycleFlow(viewModel.coin) { coin ->
            this.coin = coin
            binding.coinText.text = coin.earnedCoin.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}