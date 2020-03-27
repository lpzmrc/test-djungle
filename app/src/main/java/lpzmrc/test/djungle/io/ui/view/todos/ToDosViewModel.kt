package lpzmrc.test.djungle.io.ui.view.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.core.api.dto.ToDo
import lpzmrc.test.djungle.io.data.repository.ToDosRepository
import lpzmrc.test.djungle.io.data.storage.AuthStorage
import lpzmrc.test.djungle.io.ui.view.todos.model.ToDoView
import lpzmrc.test.djungle.io.ui.view.todos.model.ToDosResult
import org.koin.core.KoinComponent
import org.koin.core.inject

class ToDosViewModel : ViewModel(), KoinComponent {

    private val repository: ToDosRepository by inject()
    private val authStorage: AuthStorage by inject()
    val authenticationState = authStorage.authenticationState

    private val _toDosResult = MutableLiveData<ToDosResult>()

    val toDosResult: LiveData<ToDosResult> = _toDosResult
    val progress = MutableLiveData(false)

    fun fetchData() {
        viewModelScope.launch {
            progress.value = true
            _toDosResult.value = ToDosResult(listOf(ToDoView.ToDoProgress))
            val result = repository.fetchToDos()

            if (result.isSuccess) {
                _toDosResult.value = ToDosResult(result.data.toDoList())
            } else {
                _toDosResult.value = ToDosResult(
                    error = R.string.error_occurred
                )
            }
            progress.value = false
        }
    }

    fun clearData() {
        viewModelScope.launch {
            _toDosResult.value = ToDosResult(listOf(ToDoView.ToDoInvalidated))
        }
    }
}

private fun List<ToDo>?.toDoList(): List<ToDoView> = when {
    this == null || this.isEmpty() -> listOf(ToDoView.ToDoEmpty)
    else -> map { ToDoView.ToDoTask(it.id, it.title, it.completed) }
}
