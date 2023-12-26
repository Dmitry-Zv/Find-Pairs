package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.repository.GameRepository
import javax.inject.Inject

class GetGameLevel @Inject constructor(private val gameRepository: GameRepository) {

    operator fun invoke(): Int =
        gameRepository.getGameLevel()
}