package com.vc.findpairs.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.utils.Constants.COIN_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCoin(coin: Coin)

    @Query("SELECT * FROM $COIN_TABLE WHERE id = 1")
    fun getCoin(): Flow<Coin>
}