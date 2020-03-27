package lpzmrc.test.djungle.io.core.network.ktx

import okhttp3.RequestBody
import okio.Buffer
import org.json.JSONObject
import timber.log.Timber

private const val USER_EXCEPTION = "exception"
private val USER_CREDENTIALS = Pair("mario.rossi", "12345678")

internal val RequestBody.loginResult: ResponseResult
    get() {
        return try {
            getLoginParameter("username", "password").let {
                when {
                    it == USER_CREDENTIALS -> ResponseResult.AUTHORIZED
                    it.first == USER_EXCEPTION -> ResponseResult.EXCEPTION
                    else -> ResponseResult.UNAUTHORIZED
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            return ResponseResult.EXCEPTION
        }
    }

fun RequestBody.getLoginParameter(user: String, secret: String): Pair<String, String> =
    JSONObject(contentString()).run {
        Pair(optString(user), optString(secret))
    }

private fun RequestBody.contentString(): String {
    val buffer = Buffer()
    writeTo(buffer)
    return buffer.readUtf8()
}