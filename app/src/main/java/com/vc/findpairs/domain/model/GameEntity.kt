package com.vc.findpairs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vc.findpairs.utils.Constants.GAME_TABLE

@Entity(tableName = GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val countOfFields: Int,
    val countOfColumns: Int
) {
    companion object {
        val listOfGameEntity = listOf<GameEntity>(
            GameEntity(
                1, 4, 2
            ),
            GameEntity(
                2, 6, 2
            ),
            GameEntity(
                3, 6, 3
            ),
            GameEntity(
                4, 8, 4
            ),
            GameEntity(
                5, 10, 5
            ),
            GameEntity(
                6, 12, 4
            ),
            GameEntity(
                7, 16, 4
            ),
            GameEntity(
                8, 20, 4
            ),
            GameEntity(
                9, 20, 5
            ),
            GameEntity(
                10, 20, 5
            )
        )
    }
}
