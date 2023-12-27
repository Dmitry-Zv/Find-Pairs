package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.GameEntity
import com.vc.findpairs.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGameEntity @Inject constructor(private val repository: GameRepository) {

    operator fun invoke(): Flow<GameEntity> =
        repository.getGameEntity()
}