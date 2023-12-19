package com.vc.findpairs.presentation.menuscreen

import com.vc.findpairs.domain.model.GameEntity

sealed class MenuEvent {
    data class InsertListOfGameEntity(val listOfGameEntity: List<GameEntity>) : MenuEvent()
}