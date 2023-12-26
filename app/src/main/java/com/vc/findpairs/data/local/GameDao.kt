package com.vc.findpairs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.utils.Constants.GAME_TABLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameEntities(listOfGameEntities: List<GameEntity>)

    @Query("SELECT * FROM $GAME_TABLE WHERE id = :gameLevel")
    fun getGameEntity(gameLevel: Int): Flow<GameEntity>
}

//class GameCallback(
//    private val gameDao: GameDao,
//    private val coinDao: CoinDao
//) : RoomDatabase.Callback() {
//
//    private val applicationScope = CoroutineScope(SupervisorJob())
//
//    override fun onCreate(db: SupportSQLiteDatabase) {
//        super.onCreate(db)
//        applicationScope.launch(Dispatchers.IO) {
//            populateDatabase()
//        }
//    }
//
//    private suspend fun populateDatabase() {
//        gameDao.insertGameEntities(listOfGameEntities = GameEntity.listOfGameEntity)
//        coinDao.insertOrUpdateCoin(
//            coin = Coin(
//                0, 0, 0
//            )
//        )
//    }
//}