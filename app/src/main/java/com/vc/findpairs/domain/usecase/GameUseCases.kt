package com.vc.findpairs.domain.usecase

data class GameUseCases(
    val insertListOfGameEntity: InsertListOfGameEntity,
    val getGameEntity: GetGameEntity,
    val insertCoin: InsertCoin,
    val getCoin: GetCoin,
    val insertListOfGameFieldEntity: InsertListOfGameFieldEntity,
    val getListOfGameFieldEntityByGameLevel: GetListOfGameFieldEntityByGameLevel,
    val insertGameLevel: InsertGameLevel,
    val getGameLevel: GetGameLevel
)