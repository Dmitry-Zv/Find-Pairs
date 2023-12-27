package com.vc.findpairs.presentation.gamescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.domain.usecase.GameUseCases
import com.vc.findpairs.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val gameUseCases: GameUseCases) : ViewModel(),
    Event<GameEvent> {

    private val _time = MutableStateFlow("00:00")
    val time = _time.asStateFlow()

    private val _listOfGameFieldEntity = MutableStateFlow<List<GameFieldEntity>>(emptyList())
    val listOfGameField = _listOfGameFieldEntity.asStateFlow()
    private val _congratulation = MutableStateFlow(false)
    val congratulation = _congratulation.asStateFlow()
    private val _coin = MutableStateFlow<Coin>(Coin(0, 0, 0))
    val coin = _coin.asStateFlow()
    private val flippedCards = mutableListOf<GameFieldEntity>()
    private val _gameEntityState = MutableStateFlow<GameEntity?>(null)
    val gameEntityState = _gameEntityState.asStateFlow()

    private var job: Job? = null
    private var seconds: Int = 0
    private var list = emptyList<GameFieldEntity>()
    override fun onEvent(event: GameEvent) {
        when (event) {
            GameEvent.StartTimer -> startTimer()
            is GameEvent.ReverseField -> checkTwoField(
                listOfGameFieldEntity = event.listOfGameFieldEntity,
                gameFieldEntity = event.gameFieldEntity
            )

            is GameEvent.Congratulation -> congratulation(currentCoin = event.currentCoin)
            GameEvent.GetCoin -> getCoin()
            GameEvent.GetGameFieldEntity -> getGameFieldEntity()
            GameEvent.GetGameEntity -> getGameEntity()
        }
    }

    private fun getGameEntity() {
        gameUseCases.getGameEntity().onEach { gameEntity ->
            insertListOfGameFieldEntity(countOfFields = gameEntity.countOfFields)
            _gameEntityState.value = gameEntity
        }.launchIn(viewModelScope)
    }

    private suspend fun insertListOfGameFieldEntity(countOfFields: Int) {
        val listOfGameFieldEntity = GameFieldEntity.createListOfGameFieldEntity(
            countOfFields = countOfFields
        )
        gameUseCases.insertListOfGameFieldEntity(listOfGameFieldEntity = listOfGameFieldEntity)
    }

    private fun getGameFieldEntity() {
        gameUseCases.getListOfGameFieldEntityByGameLevel().onEach {
            val shuffledList = it.shuffled()
            _listOfGameFieldEntity.value = shuffledList
        }.launchIn(viewModelScope)
    }

    private fun getCoin() {
        gameUseCases.getCoin()
            .onEach { coin ->
                _coin.value = coin
            }.launchIn(viewModelScope)
    }

    private fun congratulation(currentCoin: Int) {
        job?.cancel()
        var newCurrentCoin = currentCoin
        var earnedCoin = 100
        if (seconds > 20) {
            earnedCoin = 100 - ((seconds - 20) * 5)
            if (earnedCoin < 10) earnedCoin = 10
        }
        newCurrentCoin += earnedCoin
        viewModelScope.launch {
            gameUseCases.insertCoin(
                coin = Coin(
                    1,
                    currentCoin = newCurrentCoin,
                    earnedCoin = earnedCoin
                )
            )
            _congratulation.value = true
        }
    }


    private fun checkTwoField(
        listOfGameFieldEntity: List<GameFieldEntity>,
        gameFieldEntity: GameFieldEntity,
    ) {
        viewModelScope.launch {
            var count = 0
            flippedCards.add(gameFieldEntity)
            list = listOfGameFieldEntity.map { gameFieldEntity ->
                gameFieldEntity.isRotated?.let { isRotated ->
                    if (isRotated && !gameFieldEntity.isRight) {
                        count++
                    }
                }
                gameFieldEntity
            }
            _listOfGameFieldEntity.value = list
            delay(400L)

            if (count == 2) {
                if (flippedCards[0].iconField == flippedCards[1].iconField) {
                    list = listOfGameFieldEntity.map { gameFieldEntity ->
                        gameFieldEntity.isRotated?.let { isRotated ->
                            if (isRotated) {
                                GameFieldEntity(
                                    id = gameFieldEntity.id,
                                    iconField = gameFieldEntity.iconField,
                                    isRotated = true,
                                    isRight = true
                                )
                            } else {
                                gameFieldEntity
                            }
                        } ?: gameFieldEntity
                    }
                } else {
                    list = listOfGameFieldEntity.map { gameFieldEntity ->
                        gameFieldEntity.isRotated?.let { isRotated ->
                            if (isRotated && !gameFieldEntity.isRight) {
                                GameFieldEntity(
                                    id = gameFieldEntity.id,
                                    iconField = gameFieldEntity.iconField,
                                    isRotated = false,
                                    isRight = false
                                )
                            } else if (gameFieldEntity.isRight) {
                                GameFieldEntity(
                                    id = gameFieldEntity.id,
                                    iconField = gameFieldEntity.iconField,
                                    isRotated = true,
                                    isRight = true
                                )
                            } else {
                                gameFieldEntity
                            }
                        } ?: gameFieldEntity
                    }
                }

                count = 0
                flippedCards.clear()
            }
            _listOfGameFieldEntity.value = list

        }

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