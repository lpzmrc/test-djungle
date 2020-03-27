package lpzmrc.test.djungle.io.core.network.ktx

import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.HttpURLConnection
import java.util.*

private const val MESSAGE_BAD_REQUEST = "Bad Request"
private const val MESSAGE_NO_CONTENT = "No Content"
private const val MESSAGE_NOT_FOUND = "Not Found"
private const val MESSAGE_OK = "OK"
private const val MESSAGE_UNAUTHORIZED = "Unauthorized"

private const val BODY_BAD_REQUEST = "{\"errorCode\": ${HttpURLConnection.HTTP_BAD_REQUEST}, \"message\": \"${MESSAGE_BAD_REQUEST}\"}"
private const val BODY_NOT_FOUND = "{\"errorCode\": ${HttpURLConnection.HTTP_NOT_FOUND}, \"message\": \"${MESSAGE_NOT_FOUND}\"}"
private const val BODY_UNAUTHORIZED = "{\"errorCode\": ${HttpURLConnection.HTTP_UNAUTHORIZED}, \"message\": \"${MESSAGE_UNAUTHORIZED}\"}"

private const val CONTENT_TYPE = "Content-Type"
private const val CONTENT_TYPE_APPLICATION_JSON = "application/json; charset=utf-8"
private const val CONTENT_LENGTH = "Content-Length"

val Request.AUTHORIZED_RESPONSE: Response
    get() {
        val contentType = CONTENT_TYPE_APPLICATION_JSON
        val responseBody = "{\"userId\": \"${UUID.randomUUID()}\", \"displayName\": \"Mario Rossi\"}".toResponseBody(contentType.toMediaType())
        return responseBuilder()
            .message(MESSAGE_OK)
            .headers(
                Headers.headersOf(
                    CONTENT_TYPE,
                    contentType,
                    CONTENT_LENGTH,
                    responseBody.contentLength().toString()
                )
            )
            .message("Http")
            .code(HttpURLConnection.HTTP_OK)
            .body(responseBody)
            .build()
    }

val Request.BAD_REQUEST_RESPONSE: Response
    get() {
        val contentType = CONTENT_TYPE_APPLICATION_JSON
        val responseBody = BODY_BAD_REQUEST.toResponseBody(contentType.toMediaType())
        return responseBuilder()
            .message(MESSAGE_BAD_REQUEST)
            .headers(
                Headers.headersOf(
                    CONTENT_TYPE,
                    contentType,
                    CONTENT_LENGTH,
                    responseBody.contentLength().toString()
                )
            )
            .code(HttpURLConnection.HTTP_BAD_REQUEST)
            .body(responseBody)
            .build()
    }

val Request.NO_CONTENT_RESPONSE: Response
    get() = responseBuilder()
        .message(MESSAGE_NO_CONTENT)
        .headers(
            Headers.headersOf(
                CONTENT_LENGTH,
                0.toString()
            )
        )
        .code(HttpURLConnection.HTTP_NO_CONTENT)
        .body("".toResponseBody())
        .build()

val Request.NOT_FOUND_RESPONSE: Response
    get() {
        val contentType = CONTENT_TYPE_APPLICATION_JSON
        val responseBody = BODY_NOT_FOUND.toResponseBody(contentType.toMediaType())
        return responseBuilder()
            .message(MESSAGE_NOT_FOUND)
            .headers(
                Headers.headersOf(
                    CONTENT_TYPE,
                    contentType,
                    CONTENT_LENGTH,
                    responseBody.contentLength().toString()
                )
            )
            .code(HttpURLConnection.HTTP_NOT_FOUND)
            .body(responseBody)
            .build()
    }

val Request.UNAUTHORIZED_RESPONSE: Response
    get() {
        val contentType = CONTENT_TYPE_APPLICATION_JSON
        val responseBody = BODY_UNAUTHORIZED.toResponseBody(contentType.toMediaType())
        return responseBuilder()
            .message(MESSAGE_UNAUTHORIZED)
            .headers(
                Headers.headersOf(
                    CONTENT_TYPE,
                    contentType,
                    CONTENT_LENGTH,
                    responseBody.contentLength().toString()
                )
            )
            .code(HttpURLConnection.HTTP_UNAUTHORIZED)
            .body(responseBody)
            .build()
    }

val Request.loginResponse: Response
    get() = body?.let {
        when (it.loginResult) {
            ResponseResult.EXCEPTION -> throw IOException("Something went wrong")
            ResponseResult.UNAUTHORIZED -> UNAUTHORIZED_RESPONSE
            ResponseResult.AUTHORIZED -> AUTHORIZED_RESPONSE
        }
    } ?: BAD_REQUEST_RESPONSE

private fun Request.responseBuilder() = Response.Builder()
    .request(this)
    .protocol(Protocol.HTTP_2)