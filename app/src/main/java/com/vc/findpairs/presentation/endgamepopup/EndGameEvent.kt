package com.vc.findpairs.presentation.endgamepopup

import com.vc.findpairs.domain.model.Coin

sealed class EndGameEvent {
    data object GetEarnedMoney : EndGameEvent()
    data class DoubleReward(val coin: Coin) : EndGameEvent()
    data object NextLevel : EndGameEvent()
}
