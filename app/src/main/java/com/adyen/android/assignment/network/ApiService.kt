package com.adyen.android.assignment.network

import com.adyen.android.assignment.network.model.AstronomyPictureDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("planetary/apod")
    suspend fun fetchApods(@Query("count") count: Int, @Query("api_key") apiKey: String) : List<AstronomyPictureDTO>
}