package lpzmrc.test.djungle.io.core.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.userAgent

class UserAgentInterceptor(private val customUserAgent: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.request().newBuilder().header("User-Agent", customUserAgent ?: userAgent)
            .build().run {
                chain.proceed(this)
            }
    }
}
