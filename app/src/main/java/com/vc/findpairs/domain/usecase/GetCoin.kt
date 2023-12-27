package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.Coin
import com.vc.findpairs.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoin @Inject constructor(private val coinRepository: CoinRepository) {

    operator fun invoke(): Flow<Coin> =
        coinRepository.getCoin()
}