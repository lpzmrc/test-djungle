package lpzmrc.test.djungle.io.core.api.service

import lpzmrc.test.djungle.io.core.api.dto.LoggedInUser
import lpzmrc.test.djungle.io.core.api.dto.LoginParameter
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/login")
    suspend fun login(@Body body: LoginParameter): Response<LoggedInUser>

    @POST("/logout")
    suspend fun logout(): Response<Void>
}
