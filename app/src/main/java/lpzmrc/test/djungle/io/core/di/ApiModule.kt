package lpzmrc.test.djungle.io.core.di

import lpzmrc.test.djungle.io.core.api.service.AuthService
import lpzmrc.test.djungle.io.core.api.service.GalleryService
import lpzmrc.test.djungle.io.core.api.service.ToDosService
import lpzmrc.test.djungle.io.core.network.interceptor.authInterceptor
import lpzmrc.test.djungle.io.core.network.interceptor.loggerInterceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val DEFAULT_CLIENT = "DEFAULT_CLIENT"
const val DEFAULT_RETROFIT = "DEFAULT_RETROFIT"
const val AUTH_CLIENT = "AUTH_CLIENT"
const val AUTH_RETROFIT = "AUTH_RETROFIT"
const val API_ENDPOINT = "https://jsonplaceholder.typicode.com/"

val apiModule = module {

    single(named(AUTH_RETROFIT)) {
        OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single(named(DEFAULT_RETROFIT)) {
        OkHttpClient.Builder()
            .addInterceptor(loggerInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    single<Retrofit>(named(AUTH_CLIENT)) {
        Retrofit.Builder()
            .client(get(named(AUTH_RETROFIT)))
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<Retrofit>(named(DEFAULT_CLIENT)) {
        Retrofit.Builder()
            .client(get(named(DEFAULT_RETROFIT)))
            .baseUrl(API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { createService(get(named(DEFAULT_CLIENT)), GalleryService::class.java) }
    single { createService(get(named(DEFAULT_CLIENT)), ToDosService::class.java) }
    single { createService(get(named(AUTH_CLIENT)), AuthService::class.java) }
}

private fun <T : Any> createService(retrofit: Retrofit, service: Class<T>): T {
    return retrofit.create(service)
}