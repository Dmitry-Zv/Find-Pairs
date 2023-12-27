package com.vc.findpairs.domain.repository

import com.vc.findpairs.domain.model.GameFieldEntity
import kotlinx.coroutines.flow.Flow

interface GameFieldRepository {
    suspend fun insertListOfGameFieldEntity(listOfGameFieldEntity: List<GameFieldEntity>)

    fun getListOfGameFieldEntityByGameLevel(): Flow<List<GameFieldEntity>>

    suspend fun deleteListOfGameFieldEntity()
}