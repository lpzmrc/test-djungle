package lpzmrc.test.djungle.io.ui.view.todos.model

sealed class ToDoView(val type: Type) {

    data class ToDoTask(
        val id: Long,
        val title: String,
        val completed: Boolean = false
    ) : ToDoView(Type.TASK)

    object ToDoEmpty : ToDoView(Type.EMPTY)

    object ToDoProgress : ToDoView(Type.PROGRESS)

    object ToDoInvalidated : ToDoView(Type.INVALIDATED)

    enum class Type {
        TASK, EMPTY, PROGRESS, INVALIDATED
    }
}
