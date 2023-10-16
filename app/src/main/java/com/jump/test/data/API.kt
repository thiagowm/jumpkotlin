package com.jump.test.data

import com.jump.test.model.ExitResponse
import com.jump.test.model.LoginResponse
import com.jump.test.model.ManualResponse
import retrofit2.Call
import retrofit2.http.*

interface API {

//    {{url}}/{{userId}}/
//    @GET("api/")
    @GET("posts/")
//    @GET(HTTPClient.API_KEY)
//    fun findAllVehicles( @Query("userId") userId: String = HTTPClient.API_KEY ) : Call<List<String>>
    fun findAllVehicles() : Call<List<String>>

    @POST("user/login")
    fun login(
        @Query("email") email: String = HTTPClient.email,
        @Query("password") password: String = HTTPClient.password
    ) : Call<LoginResponse>


//    @GET("/api/users/{id}")
//    fun getUser(@Path("id") id: Long?): Call<UserApiResponse?>?
    @GET("{userId}/establishment/{establishmentId}/sync/manual")
    fun getManual(
        @Header("Authorization") token: String = HTTPClient.token,
        @Path("userId") userId: Int = HTTPClient.userId,
        @Path("establishmentId") establishmentId: Int = HTTPClient.establishmentId
    ) : Call<ManualResponse>


    @POST("{userId}/establishment/{establishmentId}/session/close/{sessionId}")
    fun exitApp(
        @Header("Authorization") token: String = HTTPClient.token,
        @Path("userId") userId: Int = HTTPClient.userId,
        @Path("establishmentId") establishmentId: Int = HTTPClient.establishmentId,
        @Path("sessionId") sessionId: Int = HTTPClient.sessionId
    ) : Call<ExitResponse>

}