package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.repository.GameRepository
import javax.inject.Inject

class InsertListOfGameEntity @Inject constructor(private val repository: GameRepository) {

    suspend operator fun invoke(listOfGameEntity: List<GameEntity>) {
        repository.insertListOfGameEntity(listOfGameEntity = listOfGameEntity)
    }
}