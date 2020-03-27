package lpzmrc.test.djungle.io.data.repository

import lpzmrc.test.djungle.io.core.api.dto.ToDo
import lpzmrc.test.djungle.io.core.api.service.ToDosService
import lpzmrc.test.djungle.io.core.network.ktx.toResource
import lpzmrc.test.djungle.io.core.network.model.Resource
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class ToDosRepository : KoinComponent {

    private val service: ToDosService by inject()

    suspend fun fetchToDos(): Resource<List<ToDo>?> {
        return try {
            service.fetchToDos().run {
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