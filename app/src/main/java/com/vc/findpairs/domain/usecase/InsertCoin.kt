package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.repository.CoinRepository
import javax.inject.Inject

class InsertCoin @Inject constructor(private val coinRepository: CoinRepository) {

    suspend operator fun invoke(coin: Coin) {
        coinRepository.insertCoin(coin = coin)
    }
}