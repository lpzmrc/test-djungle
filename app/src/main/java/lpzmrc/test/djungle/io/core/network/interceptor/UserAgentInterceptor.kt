package lpzmrc.test.djungle.io.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class UserAgentInterceptor(private val agent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().header("User-Agent", agent).build().run {
            chain.proceed(this)
        }
    }
}
