package com.vc.findpairs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.model.GameFieldEntity

@Database(entities = [GameEntity::class, Coin::class, GameFieldEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao
    abstract fun getCoinDao(): CoinDao
    abstract fun getGameFieldDao(): GameFieldDao
}