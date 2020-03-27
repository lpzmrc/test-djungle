package lpzmrc.test.djungle.io.data.repository

import androidx.lifecycle.LiveData
import lpzmrc.test.djungle.io.core.api.dto.LoggedInUser
import lpzmrc.test.djungle.io.core.api.dto.LoginParameter
import lpzmrc.test.djungle.io.core.api.service.AuthService
import lpzmrc.test.djungle.io.core.network.ktx.toResource
import lpzmrc.test.djungle.io.core.network.model.Resource
import lpzmrc.test.djungle.io.core.network.model.Status
import lpzmrc.test.djungle.io.data.storage.AuthStorage
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class AuthRepository : KoinComponent {

    private val service: AuthService by inject()
    private val storage: AuthStorage by inject()
    val loggedInUser: LiveData<LoggedInUser?> = storage.loggedInUser

    suspend fun logout(): Resource<Void> {
        try {
            service.logout()
        } catch (exception: Exception) {
            Timber.e(exception)
        }
        storage.clearUser()
        return Resource(Status.SUCCESS, data = null)
    }

    suspend fun login(username: String, password: String): Resource<LoggedInUser?> {
        return try {
            service.login(LoginParameter(username, password)).run {
                if (isSuccessful) {
                    body()?.let {
                        setLoggedInUser(it)
                        toResource()
                    } ?: Resource.error(message(), errorCode = Resource.ErrorCodes.ERROR.ordinal)
                } else {
                    toResource()
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            Resource.error("An error has occurred", errorCode = Resource.ErrorCodes.EXCEPTION.ordinal)
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        storage.saveUser(loggedInUser)
    }
}
