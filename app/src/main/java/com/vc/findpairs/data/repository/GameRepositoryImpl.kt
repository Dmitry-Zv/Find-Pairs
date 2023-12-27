package com.vc.findpairs.data.repository

import android.content.SharedPreferences
import com.vc.findpairs.data.local.GameDao
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.repository.GameRepository
import com.vc.findpairs.utils.Constants.GAME_LEVEL_KEY
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameDao: GameDao,
    private val gamePreferences: SharedPreferences
) : GameRepository {
    override suspend fun insertListOfGameEntity(listOfGameEntity: List<GameEntity>) {
        gameDao.insertGameEntities(listOfGameEntity)
    }

    override fun getGameEntity(): Flow<GameEntity> {
        val gameLevel = gamePreferences.getInt(GAME_LEVEL_KEY, 1)
        return gameDao.getGameEntity(gameLevel = gameLevel)
    }

    override fun insertGameLevel(gameLevel: Int) {
        gamePreferences.edit()
            .putInt(GAME_LEVEL_KEY, gameLevel)
            .apply()
    }

    override suspend fun getLastLevel(): Int =
        gameDao.getLastLevel()

    override fun getGameLevel(): Int =
        gamePreferences.getInt(GAME_LEVEL_KEY, 1)


}