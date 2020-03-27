package lpzmrc.test.djungle.io.core.api.service

import lpzmrc.test.djungle.io.core.api.dto.Photo
import retrofit2.Response
import retrofit2.http.GET

interface GalleryService {

    @GET("/photos")
    suspend fun fetchPhotos(): Response<List<Photo>>
}
