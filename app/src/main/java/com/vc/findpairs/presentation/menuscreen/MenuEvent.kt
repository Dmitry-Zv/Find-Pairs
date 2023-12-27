package com.vc.findpairs.presentation.menuscreen


sealed class MenuEvent {
    data object GetCurrentCoin : MenuEvent()
}