package com.vc.findpairs.domain.repository

import com.vc.findpairs.domain.model.Coin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun insertCoin(coin: Coin)

    fun getCoin(): Flow<Coin>
}