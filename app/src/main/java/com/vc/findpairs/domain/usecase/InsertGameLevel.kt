package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.repository.GameRepository
import javax.inject.Inject

class InsertGameLevel @Inject constructor(private val gameRepository: GameRepository) {

    operator fun invoke(gameLevel: Int) {
        gameRepository.insertGameLevel(gameLevel = gameLevel)
    }
}