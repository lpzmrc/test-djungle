package lpzmrc.test.djungle.io.ui.view.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.core.network.model.Resource
import lpzmrc.test.djungle.io.data.repository.AuthRepository
import lpzmrc.test.djungle.io.ui.view.login.model.LoginFormState
import lpzmrc.test.djungle.io.ui.view.login.model.LoginResult
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginViewModel : ViewModel(), KoinComponent {

    private val repository: AuthRepository by inject()
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    val username = MutableLiveData<String?>()
    val password = MutableLiveData<String?>()
    val progress = MutableLiveData(false)

    fun login() {
        viewModelScope.launch {
            progress.value = true
            val result = repository.login(username.value.orEmpty(), password.value.orEmpty())

            if (result.isSuccess) {
                _loginResult.value = LoginResult(success = true)
            } else {
                _loginResult.value = LoginResult(
                    error =
                    when (result.errorCode) {
                        Resource.ErrorCodes.EXCEPTION.ordinal -> R.string.error_occurred
                        else -> R.string.login_failed
                    }
                )
            }
            progress.value = false
        }
    }

    fun checkIfCanLogin() {
        if (isUserNameValid(username.value.orEmpty()) && isPasswordValid(password.value.orEmpty())) {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun loginDataChanged() {
        if (!isUserNameValid(username.value.orEmpty())) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password.value.orEmpty())) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
