package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.repository.GameRepository
import javax.inject.Inject

class GetLastLevel @Inject constructor(private val gameRepository: GameRepository) {

    suspend operator fun invoke(): Int =
        gameRepository.getLastLevel()
}