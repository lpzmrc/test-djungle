package lpzmrc.test.djungle.io.ui.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import lpzmrc.test.djungle.io.core.auth.AuthHelper

class MainViewModel : ViewModel() {
    val authenticationState = AuthHelper.authenticationState.map { it }
}
