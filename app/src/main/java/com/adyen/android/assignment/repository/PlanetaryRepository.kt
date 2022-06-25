package com.adyen.android.assignment.repository

import com.adyen.android.assignment.domain.model.AstronomyPicture

interface PlanetaryRepository {
    suspend fun fetchApodImages(count: Int) : List<AstronomyPicture>
}