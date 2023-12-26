package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.repository.GameFieldRepository
import javax.inject.Inject

class DeleteListOfGameFieldEntity @Inject constructor(private val gameFieldRepository: GameFieldRepository) {

    suspend operator fun invoke() {
        gameFieldRepository.deleteListOfGameFieldEntity()
    }
}