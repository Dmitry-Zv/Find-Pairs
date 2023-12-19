package com.vc.findpairs.domain.repository

import com.vc.findpairs.domain.model.GameEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    suspend fun insertListOfGameEntity(listOfGameEntity: List<GameEntity>)

    fun getGameEntity(gameLevel: Int): Flow<GameEntity>
}