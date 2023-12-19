package com.vc.findpairs.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vc.findpairs.domain.model.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun getGameDao(): GameDao
}