package lpzmrc.test.djungle.io.data.storage

import lpzmrc.test.djungle.io.core.api.dto.LoggedInUser
import lpzmrc.test.djungle.io.core.auth.AuthHelper

class AuthStorage {
    val authenticationState = AuthHelper.authenticationState
    val loggedInUser = AuthHelper.loggedInUser
    fun saveUser(data: LoggedInUser) = AuthHelper.saveUser(data)
    fun clearUser() = AuthHelper.clearUser()
}
