package lpzmrc.test.djungle.io.data.di

import lpzmrc.test.djungle.io.data.repository.GalleryRepository
import lpzmrc.test.djungle.io.data.repository.AuthRepository
import lpzmrc.test.djungle.io.data.storage.AuthStorage
import lpzmrc.test.djungle.io.data.repository.ToDosRepository
import org.koin.dsl.module

val dataModule = module {

    single { AuthRepository() }
    single { GalleryRepository() }
    single { ToDosRepository() }

    single { AuthStorage() }
}
