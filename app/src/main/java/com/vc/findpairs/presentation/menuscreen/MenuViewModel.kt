package com.vc.findpairs.presentation.menuscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.model.GameFieldEntity
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
class MenuViewModel @Inject constructor(private val gameUseCases: GameUseCases) : ViewModel(),
    Event<MenuEvent> {

    private val _currentCoin = MutableStateFlow(0)
    val currentCoin = _currentCoin.asStateFlow()
    override fun onEvent(event: MenuEvent) {
        when (event) {
            MenuEvent.GetCurrentCoin -> getCurrentCoin()
        }
    }

    private fun getCurrentCoin() {
        gameUseCases.getCoin()
            .onEach { coin ->
                _currentCoin.value = coin.currentCoin
            }
    }



}