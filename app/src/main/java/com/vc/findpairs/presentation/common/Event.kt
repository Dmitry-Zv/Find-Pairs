package com.vc.findpairs.presentation.common

interface Event<E> {
    fun onEvent(event: E)
}