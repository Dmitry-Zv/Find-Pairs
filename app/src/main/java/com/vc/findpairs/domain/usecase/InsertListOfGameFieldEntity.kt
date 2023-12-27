package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.domain.repository.GameFieldRepository
import javax.inject.Inject

class InsertListOfGameFieldEntity @Inject constructor(private val gameFieldRepository: GameFieldRepository) {

    suspend operator fun invoke(listOfGameFieldEntity: List<GameFieldEntity>) {
        gameFieldRepository.insertListOfGameFieldEntity(listOfGameFieldEntity = listOfGameFieldEntity)
    }
}