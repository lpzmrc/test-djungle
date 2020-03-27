package lpzmrc.test.djungle.io.core.api.service

import lpzmrc.test.djungle.io.core.api.dto.ToDo
import retrofit2.Response
import retrofit2.http.GET

interface ToDosService {

    @GET("/todos")
    suspend fun fetchToDos(): Response<List<ToDo>>
}
