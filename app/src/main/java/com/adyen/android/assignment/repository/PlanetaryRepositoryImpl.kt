package com.adyen.android.assignment.repository

import com.adyen.android.assignment.BuildConfig
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.local.model.AstronomyPictureDAO
import com.adyen.android.assignment.network.ApiService
import com.adyen.android.assignment.network.model.AstronomyPictureDTOMapper

class PlanetaryRepositoryImpl(
    private val apiService: ApiService,
    private val dtoMapper: AstronomyPictureDTOMapper,
    private val astronomyPictureDAO: AstronomyPictureDAO
) : PlanetaryRepository {

    companion object {
        private const val PAGE_COUNT = 20
    }

    override suspend fun fetchApodImages(): List<AstronomyPicture> {
        val dbApods = astronomyPictureDAO.getApodList()
        if (dbApods.isEmpty() || !dbApods.any{ !it.favorite }) {
            val networkApods =
                dtoMapper.toDomainList(apiService.fetchApods(PAGE_COUNT, BuildConfig.API_KEY))
                    .filter { it.mediaType == AstronomyPicture.MediaType.IMAGE.value }
                    .toMutableList()
            astronomyPictureDAO.insertApods(networkApods)
        }
        return astronomyPictureDAO.getApodList()
    }

    override suspend fun fetchApodDetails(id: String): AstronomyPicture? {
        return astronomyPictureDAO.getApod(id)
    }

    override suspend fun favoriteApod(id: String, shouldFavorite: Boolean): AstronomyPicture? {
        astronomyPictureDAO.updateApodFavorite(id, shouldFavorite)
        return astronomyPictureDAO.getApod(id)
    }

    override suspend fun clearNonFavoriteApods() {
        astronomyPictureDAO.deleteNonFavoriteApods()
    }
}