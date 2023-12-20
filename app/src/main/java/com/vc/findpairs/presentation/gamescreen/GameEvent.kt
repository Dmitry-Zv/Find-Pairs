package com.vc.findpairs.presentation.gamescreen

import com.vc.findpairs.domain.model.GameField

sealed class GameEvent {
    data object StartTimer : GameEvent()
    data class CheckTwoField(
        val listOfGameField: List<GameField>,
        val gameField: GameField,
        val position: Int
    ) : GameEvent()
}
