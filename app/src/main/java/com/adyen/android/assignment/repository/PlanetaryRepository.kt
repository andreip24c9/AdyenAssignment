package com.adyen.android.assignment.repository

import com.adyen.android.assignment.domain.model.AstronomyPicture

interface PlanetaryRepository {
    suspend fun fetchApodImages() : List<AstronomyPicture>
    suspend fun fetchApodDetails(id: String): AstronomyPicture?
    suspend fun favoriteApod(id: String, shouldFavorite: Boolean): AstronomyPicture?
    suspend fun clearNonFavoriteApods()
}