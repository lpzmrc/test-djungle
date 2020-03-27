package lpzmrc.test.djungle.io.core.network.model

data class Resource<out T>(val status: Status, val data: T?, val error: String? = null, val errorCode: Int? = null) {
    val isSuccess
        get() = status == Status.SUCCESS

    companion object {
        fun <T> success(payload: T) = Resource(Status.SUCCESS, payload, null)
        fun <T> error(error: String?, payload: T? = null, errorCode: Int? = null) = Resource<T>(Status.ERROR, payload, error, errorCode)
    }

    enum class ErrorCodes {
        EXCEPTION,
        ERROR
    }
}