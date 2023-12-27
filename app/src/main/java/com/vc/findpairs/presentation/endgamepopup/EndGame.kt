package com.vc.findpairs.presentation.endgamepopup

sealed class EndGame {
    data object Default : EndGame()
    data object GoToGameFragment : EndGame()
    data object GoToMenuFragment : EndGame()
}
