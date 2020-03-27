package lpzmrc.test.djungle.io.core.network.ktx

import lpzmrc.test.djungle.io.core.network.model.Resource
import retrofit2.Response

fun <T> Response<T>.toResource(): Resource<T?> {
    if (isSuccessful)
        return Resource.success(body())

    return Resource.error(message())
}

enum class ResponseResult {
    EXCEPTION, UNAUTHORIZED, AUTHORIZED
}