package com.jump.test.data

import com.jump.test.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HTTPClient {

    private const val BASE_URL = "https://dev.app.jumpparkapi.com.br/api/"
    lateinit var email: String
    lateinit var password: String
    lateinit var token: String
    var userId: Int = 0
    var sessionId: Int = 0
    var establishmentId: Int = 0

    private fun httpCliente() : OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    }

    fun retrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client( httpCliente() )
        .build()



}