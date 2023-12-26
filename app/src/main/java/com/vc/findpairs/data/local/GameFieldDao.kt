package com.vc.findpairs.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface GameFieldDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfGameFieldEntity(listOfGameFieldEntity: List<GameFieldEntity>)

    @Query("SELECT * FROM ${Constants.GAME_FIELD_TABLE}")
    fun getListOfGameFieldEntityByGameLevel(): Flow<List<GameFieldEntity>>

    @Query("DELETE FROM ${Constants.GAME_FIELD_TABLE}")
    suspend fun deleteListOfGameFieldEntity()
}