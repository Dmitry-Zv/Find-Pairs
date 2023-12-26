package com.vc.findpairs.di

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vc.findpairs.data.local.CoinDao
import com.vc.findpairs.data.local.GameDao
import com.vc.findpairs.data.local.GameDatabase
import com.vc.findpairs.data.local.GameFieldDao
import com.vc.findpairs.data.repository.CoinRepositoryImpl
import com.vc.findpairs.data.repository.GameFieldRepositoryImpl
import com.vc.findpairs.data.repository.GameRepositoryImpl
import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.repository.CoinRepository
import com.vc.findpairs.domain.repository.GameFieldRepository
import com.vc.findpairs.domain.repository.GameRepository
import com.vc.findpairs.domain.usecase.GameUseCases
import com.vc.findpairs.domain.usecase.GetCoin
import com.vc.findpairs.domain.usecase.GetGameEntity
import com.vc.findpairs.domain.usecase.GetGameLevel
import com.vc.findpairs.domain.usecase.GetListOfGameFieldEntityByGameLevel
import com.vc.findpairs.domain.usecase.InsertCoin
import com.vc.findpairs.domain.usecase.InsertGameLevel
import com.vc.findpairs.domain.usecase.InsertListOfGameEntity
import com.vc.findpairs.domain.usecase.InsertListOfGameFieldEntity
import com.vc.findpairs.utils.Constants
import com.vc.findpairs.utils.Constants.GAME_DB
import com.vc.findpairs.utils.Constants.GAME_PREFERENCES
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GameDatabase =
        Room.databaseBuilder(context, GameDatabase::class.java, GAME_DB)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    try {
                        db.beginTransaction()
                        GameEntity.listOfGameEntity.forEach {
                            val cv = ContentValues()
                            cv.put("id", it.id)
                            cv.put("countOfFields", it.countOfFields)
                            cv.put("countOfColumns", it.countOfColumns)
                            db.insert(Constants.GAME_TABLE, SQLiteDatabase.CONFLICT_IGNORE, cv)
                            cv.clear()
                        }
                        val cv = ContentValues()
                        cv.put("id", 0)
                        cv.put("currentCoin", 0)
                        cv.put("earnedCoin", 0)
                        db.insert(Constants.COIN_TABLE, SQLiteDatabase.CONFLICT_IGNORE, cv)
                        cv.clear()
                        db.setTransactionSuccessful()
                        db.endTransaction()
                    } catch (e: Exception) {
                        db.endTransaction()
                    }

                }
            })
            .build()

//    @Provides
//    @Singleton
//    fun provideGameCallback(
//        gameDao: GameDao,
//        coinDao: CoinDao
//    ): GameCallback = GameCallback(
//        gameDao = gameDao,
//        coinDao = coinDao
//    )

    @Provides
    @Singleton
    fun provideGameDao(gameDatabase: GameDatabase): GameDao =
        gameDatabase.getGameDao()

    @Provides
    @Singleton
    fun provideGameFieldDao(gameDatabase: GameDatabase): GameFieldDao =
        gameDatabase.getGameFieldDao()


    @Provides
    @Singleton
    fun provideCoinDaoGameDao(gameDatabase: GameDatabase): CoinDao =
        gameDatabase.getCoinDao()


    @Provides
    @Singleton
    fun provideGameUseCases(
        repository: GameRepository,
        coinRepository: CoinRepository,
        gameFieldRepository: GameFieldRepository
    ) =
        GameUseCases(
            insertListOfGameEntity = InsertListOfGameEntity(repository),
            getGameEntity = GetGameEntity(repository),
            insertCoin = InsertCoin(coinRepository),
            getCoin = GetCoin(coinRepository),
            insertListOfGameFieldEntity = InsertListOfGameFieldEntity(gameFieldRepository),
            getListOfGameFieldEntityByGameLevel = GetListOfGameFieldEntityByGameLevel(
                gameFieldRepository
            ),
            insertGameLevel = InsertGameLevel(repository),
            getGameLevel = GetGameLevel(repository)
        )

    @Provides
    @Singleton
    fun provideGameLevelSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(GAME_PREFERENCES, Context.MODE_PRIVATE)

}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGameRepository_toGameRepositoryImpl(gameRepositoryImpl: GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    fun bindCoinRepository_toCionRepositoryImpl(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository

    @Binds
    @Singleton
    fun bindGameFieldRepository_toGameFieldRepositoryImpl(gameFieldRepositoryImpl: GameFieldRepositoryImpl): GameFieldRepository
}