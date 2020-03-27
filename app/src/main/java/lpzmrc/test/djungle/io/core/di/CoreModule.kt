package lpzmrc.test.djungle.io.core.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module

val coreModule = module {
    factory { GsonBuilder().create() }
}