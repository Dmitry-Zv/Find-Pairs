package com.vc.findpairs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vc.findpairs.utils.Constants.GAME_TABLE

@Entity(tableName = GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val countOfRows: Int,
    val countOfColumns: Int
) {
    companion object {
        val listOfGameEntity = listOf<GameEntity>(
            GameEntity(
                1, 2, 2
            ),
            GameEntity(
                2, 3, 2
            ),
            GameEntity(
                3, 3, 3
            ),
            GameEntity(
                4, 4, 2
            ),
            GameEntity(
                5, 4, 3
            ),
            GameEntity(
                6, 4, 4
            ),
            GameEntity(
                7, 5, 2
            ),
            GameEntity(
                8, 5, 3
            ),
            GameEntity(
                9, 5, 4
            ),
            GameEntity(
                10, 5, 5
            )
        )
    }
}
