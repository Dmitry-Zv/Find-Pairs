package com.vc.findpairs.presentation.menuscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.usecase.GameUseCases
import com.vc.findpairs.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val gameUseCases: GameUseCases) : ViewModel(),
    Event<MenuEvent> {
    override fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.InsertListOfGameEntity -> insert(listOfGameEntity = event.listOfGameEntity)
        }
    }

    private fun insert(listOfGameEntity: List<GameEntity>) {
        viewModelScope.launch {
            gameUseCases.insertListOfGameEntity(listOfGameEntity = listOfGameEntity)
        }
    }


}