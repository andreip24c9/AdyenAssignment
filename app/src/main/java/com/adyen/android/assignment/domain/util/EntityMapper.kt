package com.adyen.android.assignment.domain.util

interface EntityMapper<Dto, DomainModel> {
    fun mapToDomainModel(dto: Dto): DomainModel
}