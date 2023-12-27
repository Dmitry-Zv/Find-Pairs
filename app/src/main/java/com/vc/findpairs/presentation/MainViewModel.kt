package com.vc.findpairs.presentation

import androidx.lifecycle.ViewModel
import com.vc.findpairs.presentation.common.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), Event<Navigation> {

    private val _navigationState = MutableStateFlow<Navigation>(Navigation.OnMenuFragment)
    val navigationState = _navigationState.asStateFlow()

    override fun onEvent(event: Navigation) {
        when (event) {
            Navigation.OnEndGameFragment -> performEndGameFragment()
            Navigation.OnGameFragment -> performGameFragment()
            Navigation.OnMenuFragment -> performMenuFragment()
        }
    }

    private fun performMenuFragment() {
        _navigationState.value = Navigation.OnMenuFragment
    }

    private fun performGameFragment() {
        _navigationState.value = Navigation.OnGameFragment
    }

    private fun performEndGameFragment() {
        _navigationState.value = Navigation.OnEndGameFragment
    }


}

sealed class Navigation {
    data object OnMenuFragment : Navigation()
    data object OnGameFragment : Navigation()
    data object OnEndGameFragment : Navigation()
}