package com.vc.findpairs.presentation.gamescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vc.findpairs.domain.model.GameField
import com.vc.findpairs.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel(), Event<GameEvent> {

    private val _time = MutableStateFlow<String>("00:00")
    val time = _time.asStateFlow()

    private val _listOfGameField = MutableStateFlow<List<GameField>>(emptyList())
    val listOfGameField = _listOfGameField.asStateFlow()

    private var job: Job? = null
    private var seconds: Int = 0
    private var gameField: GameField? = null
    private var position: Int? = null
    private var list = emptyList<GameField>()
    override fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.StartTimer -> startTimer()
            is GameEvent.CheckTwoField -> checkTwoField(
                listOfGameField = event.listOfGameField,
                gameField = event.gameField,
                position = event.position
            )
        }
    }

    private fun checkTwoField(
        listOfGameField: List<GameField>,
        gameField: GameField,
        position: Int
    ) {
//        if (this.position != null && this.position != position && this.gameField != null) {
//            if (this.gameField == gameField) {
////                Log.d("GAME_FIELD", "${this.gameField}  $gameField")
//                val list = listOfGameField.mapIndexed { index, it ->
//                    if (index == position || index == this.position) {
//                        gameField
//                    } else {
//                        GameField(it.iconField, false)
//                    }
//                }
//
//                _listOfGameField.value = list
//            } else {
//                val list = listOfGameField.map {
//                    GameField(it.iconField, it.isRotated)
//                }
//                _listOfGameField.value = list
//            }
//            this.gameField = null
//            this.position = null
//        } else {
//            val list = listOfGameField.map {
//                GameField(it.iconField, it.isRotated)
//            }
//            _listOfGameField.value = list
//            this.position = position
//            this.gameField = gameField
//        }
        var count = 0
        list = listOfGameField.map {
            if (it.isRotated && !it.isRight) {
                count++
            }
            it
        }
        println("GAME_FIELD_LIST: $list count: $count")
        if (count == 2) {
            count = 0
            list = listOfGameField.map {
                if (it.isRotated) {
                    GameField(iconField = it.iconField, isRotated = true, isRight = true)
                } else {
                    it
                }
            }
            println("GAME_FIELD_LIST: $list")
        }

        _listOfGameField.value = list
    }

    private fun startTimer() {
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                updateTimer()
                delay(1000L)
            }
        }
    }

    private fun updateTimer() {
        _time.value = formatTime(seconds++)
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}