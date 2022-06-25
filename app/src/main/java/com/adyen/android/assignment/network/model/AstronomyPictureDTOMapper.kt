package com.adyen.android.assignment.network.model

import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.domain.util.EntityMapper

class AstronomyPictureDTOMapper : EntityMapper<AstronomyPictureDTO, AstronomyPicture> {
    override fun mapToDomainModel(dto: AstronomyPictureDTO): AstronomyPicture {
        return AstronomyPicture(
            id = Regex("[^A-Za-z0-9]").replace("${dto.date}${dto.title}", ""),
            title = dto.title,
            date = dto.date,
            explanation = dto.explanation,
            mediaType = dto.mediaType,
            hdUrl = dto.hdUrl,
            url = dto.url
        )
    }

    fun toDomainList(dtoList: List<AstronomyPictureDTO>): List<AstronomyPicture> {
        return dtoList.map { mapToDomainModel(it) }
    }
}