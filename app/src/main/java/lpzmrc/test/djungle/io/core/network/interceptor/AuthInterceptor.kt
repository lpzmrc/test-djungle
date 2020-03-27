package lpzmrc.test.djungle.io.core.network.interceptor

import lpzmrc.test.djungle.io.core.network.ktx.NO_CONTENT_RESPONSE
import lpzmrc.test.djungle.io.core.network.ktx.NOT_FOUND_RESPONSE
import lpzmrc.test.djungle.io.core.network.ktx.loginResponse
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthInterceptor : Interceptor {

    companion object {
        const val LOGOUT_PATH = "LOGOUT"
        const val LOGIN_PATH = "LOGIN"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.request().run {
        Thread.sleep(1000L)
        url.pathSegments.lastOrNull().let {
            when (it?.toUpperCase(Locale.US)) {
                LOGIN_PATH -> loginResponse
                LOGOUT_PATH -> NO_CONTENT_RESPONSE
                else -> NOT_FOUND_RESPONSE
            }
        }
    }
}
