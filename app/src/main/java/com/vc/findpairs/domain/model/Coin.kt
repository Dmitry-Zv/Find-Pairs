package com.vc.findpairs.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vc.findpairs.utils.Constants.COIN_TABLE

@Entity(tableName = COIN_TABLE)
data class Coin(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val currentCoin: Int,
    val earnedCoin: Int
)