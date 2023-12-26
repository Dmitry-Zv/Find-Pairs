package com.vc.findpairs.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class GameEntityWithGameFieldEntities(
    @Embedded
    val gameEntity: GameEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "gameLevel"
    )
    val listOfGameFieldEntity:List<GameFieldEntity>
)