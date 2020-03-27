package lpzmrc.test.djungle.io.core.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor

val loggerInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val authInterceptor = AuthInterceptor()