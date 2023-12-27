package com.vc.findpairs.presentation.endgamepopup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.usecase.GameUseCases
import com.vc.findpairs.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EndGameViewModel @Inject constructor(private val gameUseCases: GameUseCases) : ViewModel(),
    Event<EndGameEvent> {

    private val _coin = MutableStateFlow(Coin(1, 0, 0))
    val coin = _coin.asStateFlow()

    private val _endGameState = MutableStateFlow<EndGame>(EndGame.Default)
    val endGameState = _endGameState.asStateFlow()
    override fun onEvent(event: EndGameEvent) {
        when (event) {
            is EndGameEvent.DoubleReward -> doubleReward(coin = event.coin)
            EndGameEvent.GetEarnedMoney -> getEarnedCoin()
            EndGameEvent.NextLevel -> nextLevel()
        }
    }

    private fun getEarnedCoin() {
        gameUseCases.getCoin()
            .onEach { coin ->
                _coin.value = coin
            }.launchIn(viewModelScope)
    }

    private fun nextLevel() {
        viewModelScope.launch {
            var gameLevel = gameUseCases.getGameLevel()
            if (gameLevel == gameUseCases.getLastLevel()) {
                gameUseCases.deleteListOfGameFieldEntity()
                gameUseCases.insertGameLevel(gameLevel = 1)
                _endGameState.value = EndGame.GoToMenuFragment
            } else {
                gameUseCases.insertGameLevel(gameLevel = ++gameLevel)
                _endGameState.value = EndGame.GoToGameFragment
            }
        }
    }

    private fun doubleReward(coin: Coin) {
        val doubleEarnedCoin = coin.earnedCoin * 2
        val newCurrentCoin = coin.currentCoin + doubleEarnedCoin
        viewModelScope.launch {
            gameUseCases.insertCoin(
                coin = Coin(
                    1,
                    currentCoin = newCurrentCoin,
                    earnedCoin = doubleEarnedCoin
                )
            )
            getEarnedCoin()
        }
    }


}