package com.vc.findpairs.presentation.gamescreen

import com.vc.findpairs.domain.model.GameFieldEntity

sealed class GameEvent {
    data object StartTimer : GameEvent()
    data class ReverseField(
        val listOfGameFieldEntity: List<GameFieldEntity>,
        val gameFieldEntity: GameFieldEntity,
        val position: Int
    ) : GameEvent()

    data class Congratulation(val currentCoin: Int) : GameEvent()
    data object GetCoin : GameEvent()
    data object GetGameFieldEntity : GameEvent()
    data object GetGameEntity : GameEvent()

}
