package lpzmrc.test.djungle.io.data.repository

import lpzmrc.test.djungle.io.core.api.dto.Photo
import lpzmrc.test.djungle.io.core.api.service.GalleryService
import lpzmrc.test.djungle.io.core.network.ktx.toResource
import lpzmrc.test.djungle.io.core.network.model.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class GalleryRepository : KoinComponent {
    private val service: GalleryService by inject()

    suspend fun fetchPhotos(): Resource<List<Photo>?> {
        return try {
            service.fetchPhotos().run {
                if (isSuccessful) {
                    body()?.let {
                        toResource()
                    } ?: Resource.error(message(), errorCode = Resource.ErrorCodes.ERROR.ordinal)
                } else {
                    toResource()
                }
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            Resource.error("An error has occurred", errorCode = Resource.ErrorCodes.EXCEPTION.ordinal)
        }
    }
}
