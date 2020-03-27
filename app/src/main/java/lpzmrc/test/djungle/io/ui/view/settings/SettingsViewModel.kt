package lpzmrc.test.djungle.io.ui.view.settings

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.data.repository.AuthRepository
import lpzmrc.test.djungle.io.ui.view.settings.model.LogoutResult
import lpzmrc.test.djungle.io.ui.view.settings.model.SettingsUserView
import org.koin.core.KoinComponent
import org.koin.core.inject

class SettingsViewModel : ViewModel(), KoinComponent {

    private val repository: AuthRepository by inject()
    private val _logoutResult = MutableLiveData<LogoutResult>()

    val logoutResult: LiveData<LogoutResult> = _logoutResult
    val loggedInUser: LiveData<SettingsUserView?> = repository.loggedInUser.map {
        it?.let {
            SettingsUserView(
                it.displayName
            )
        }
    }
    val progress = MutableLiveData(false)

    fun logout() {
        viewModelScope.launch {
            progress.value = true
            val result = repository.logout()
            if (result.isSuccess) {
                _logoutResult.value = LogoutResult(success = true)
            } else {
                _logoutResult.value = LogoutResult(error = R.string.logout_failed)
            }
            progress.value = false
        }
    }
}
