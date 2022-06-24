package com.adyen.android.assignment.network

import com.adyen.android.assignment.domain.model.AstronomyPicture
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("planetary/apod")
    suspend fun fetchApods(@Query("count") count: Int, @Query("api_key") apiKey: String) : Response<List<AstronomyPicture>>
}