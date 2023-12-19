package com.vc.findpairs.data.repository

import com.vc.findpairs.data.local.GameDao
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameDao: GameDao) : GameRepository {
    override suspend fun insertListOfGameEntity(listOfGameEntity: List<GameEntity>) {
        gameDao.insertGameEntities(listOfGameEntity)
    }

    override fun getGameEntity(gameLevel: Int): Flow<GameEntity> =
        gameDao.getGameEntity(gameLevel = gameLevel)

}