package com.vc.findpairs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.utils.Constants.GAME_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameEntities(listOfGameEntities: List<GameEntity>)

    @Query("SELECT * FROM $GAME_TABLE WHERE id = :gameLevel")
    fun getGameEntity(gameLevel: Int): Flow<GameEntity>
}