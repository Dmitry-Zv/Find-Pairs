package com.vc.findpairs.data.repository

import com.vc.findpairs.data.local.GameFieldDao
import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.domain.repository.GameFieldRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameFieldRepositoryImpl @Inject constructor(private val gameFieldDao: GameFieldDao) :
    GameFieldRepository {
    override suspend fun insertListOfGameFieldEntity(listOfGameFieldEntity: List<GameFieldEntity>) {
        gameFieldDao.insertListOfGameFieldEntity(listOfGameFieldEntity = listOfGameFieldEntity)
    }

    override fun getListOfGameFieldEntityByGameLevel(): Flow<List<GameFieldEntity>> =
        gameFieldDao.getListOfGameFieldEntityByGameLevel()

    override suspend fun deleteListOfGameFieldEntity() {
        gameFieldDao.deleteListOfGameFieldEntity()
    }

}