package com.adyen.android.assignment.repository

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.network.ApiService
import com.adyen.android.assignment.network.model.AstronomyPictureDTOMapper

class PlanetaryRepositoryImpl(
    private val apiService: ApiService,
    private val dtoMapper: AstronomyPictureDTOMapper
) : PlanetaryRepository {

    private var cachedApods = mutableListOf<AstronomyPicture>()

    override suspend fun fetchApodImages(count: Int): List<AstronomyPicture> {
        cachedApods = dtoMapper.toDomainList(apiService.fetchApods(count, BuildConfig.API_KEY))
            .filter { it.mediaType == AstronomyPicture.MediaType.IMAGE.value }.toMutableList()
        return cachedApods
    }

    override suspend fun fetchApodDetails(id: String): AstronomyPicture? {
        return cachedApods.firstOrNull { it.id == id }
    }
}