package lpzmrc.test.djungle.io.core.api.dto

data class ToDo(
    val userId: Long,
    val id: Long,
    val title: String,
    val completed: Boolean = false
)