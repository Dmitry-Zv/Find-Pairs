package com.vc.findpairs.domain.usecase

import com.vc.findpairs.domain.model.GameFieldEntity
import com.vc.findpairs.domain.repository.GameFieldRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListOfGameFieldEntityByGameLevel @Inject constructor(private val gameFieldRepository: GameFieldRepository) {

    operator fun invoke(): Flow<List<GameFieldEntity>> =
        gameFieldRepository.getListOfGameFieldEntityByGameLevel()
}