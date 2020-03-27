package lpzmrc.test.djungle.io.core.auth

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import lpzmrc.test.djungle.io.core.api.dto.LoggedInUser
import lpzmrc.test.djungle.io.data.model.AuthenticationState
import org.koin.core.KoinComponent
import org.koin.core.inject

object AuthHelper : KoinComponent {

    private const val APP_AUTH = "APP_AUTH"
    private const val AUTH_USER = "AUTH_USER"

    private val context: Context by inject()
    private val gson: Gson = Gson()

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        APP_AUTH,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _authenticationState = MutableLiveData<AuthenticationState>()
    private val _loggedInUser = MediatorLiveData<LoggedInUser?>()

    val authenticationState: LiveData<AuthenticationState> = _authenticationState
    val loggedInUser: LiveData<LoggedInUser?> = _loggedInUser

    init {
        getStoredUser().let { storedUser ->
            _authenticationState.postValue(
                when {
                    storedUser != null -> AuthenticationState.AUTHENTICATED
                    else -> AuthenticationState.UNAUTHENTICATED
                }
            )
            _loggedInUser.postValue(storedUser)
        }
    }

    fun getStoredUser(): LoggedInUser? {
        val userJson = sharedPreferences.getString(AUTH_USER, null)
        if (userJson != null) {
            return gson.fromJson(userJson, LoggedInUser::class.java)
        }
        return userJson
    }

    fun clearUser() {
        clearAll()
        _authenticationState.postValue(AuthenticationState.UNAUTHENTICATED)
        _loggedInUser.postValue(null)
    }

    fun saveUser(data: LoggedInUser) {
        sharedPreferences.edit(commit = true) {
            putString(AUTH_USER, gson.toJson(data))
            _loggedInUser.postValue(data)
            _authenticationState.postValue(AuthenticationState.AUTHENTICATED)
        }
    }

    private fun clearAll() {
        sharedPreferences.edit().clear().commit()
    }
}