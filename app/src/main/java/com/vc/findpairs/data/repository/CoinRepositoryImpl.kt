package com.vc.findpairs.data.repository

import com.vc.findpairs.data.local.CoinDao
import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val dao: CoinDao) :
    CoinRepository {
    override suspend fun insertCoin(coin: Coin) {
        dao.insertOrUpdateCoin(coin = coin)
    }

    override fun getCoin(): Flow<Coin> =
        dao.getCoin()
}