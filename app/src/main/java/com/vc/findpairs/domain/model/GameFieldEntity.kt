package com.vc.findpairs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vc.findpairs.R
import com.vc.findpairs.utils.Constants.GAME_FIELD_TABLE

@Entity(tableName = GAME_FIELD_TABLE)
data class GameFieldEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val iconField: Int,
    val isRotated: Boolean?,
    val isRight: Boolean
) {
    companion object {
        fun createListOfGameFieldEntity(countOfFields: Int): List<GameFieldEntity> {
            val listOfDrawableRes = listOf(
                R.drawable.cake,
                R.drawable.elephant,
                R.drawable.football,
                R.drawable.guitar,
                R.drawable.gun,
                R.drawable.rock,
                R.drawable.rocket,
                R.drawable.sun,
                R.drawable.plane,
                R.drawable.sea
            )
            val shuffledDrawableList = listOfDrawableRes.shuffled()
            val listOfGameFieldEntity = mutableListOf<GameFieldEntity>()
            for ((index, i) in (1..countOfFields step 2).withIndex()) {
                var count = i
                println("GAME_FIELDS: $index")

                val randomDrawableResource = shuffledDrawableList[index]
                listOfGameFieldEntity.add(
                    GameFieldEntity(
                        id = count,
                        iconField = randomDrawableResource,
                        isRotated = null,
                        isRight = false,
                    )
                )
                listOfGameFieldEntity.add(
                    GameFieldEntity(
                        id = ++count,
                        iconField = randomDrawableResource,
                        isRotated = null,
                        isRight = false,
                    )
                )
            }
            return listOfGameFieldEntity.toList()
        }
    }
}
