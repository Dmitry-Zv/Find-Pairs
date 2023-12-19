package com.vc.findpairs.di

import android.content.Context
import androidx.room.Room
import com.vc.findpairs.data.local.GameDao
import com.vc.findpairs.data.local.GameDatabase
import com.vc.findpairs.data.repository.GameRepositoryImpl
import com.vc.findpairs.domain.repository.GameRepository
import com.vc.findpairs.domain.usecase.GameUseCases
import com.vc.findpairs.domain.usecase.GetGameEntity
import com.vc.findpairs.domain.usecase.InsertListOfGameEntity
import com.vc.findpairs.utils.Constants.GAME_DB
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase =
        Room.databaseBuilder(context, GameDatabase::class.java, GAME_DB)
            .build()

    @Provides
    @Singleton
    fun provideGameDao(gameDatabase: GameDatabase): GameDao =
        gameDatabase.getGameDao()

    @Provides
    @Singleton
    fun provideGameUseCases(repository: GameRepository) =
        GameUseCases(
            insertListOfGameEntity = InsertListOfGameEntity(repository),
            getGameEntity = GetGameEntity(repository)
        )
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindGameRepository_toGameRepositoryImpl(gameRepositoryImpl: GameRepositoryImpl): GameRepository
}