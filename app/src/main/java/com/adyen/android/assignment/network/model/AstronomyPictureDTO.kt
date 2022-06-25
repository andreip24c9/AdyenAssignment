package com.adyen.android.assignment.network.model

import com.squareup.moshi.Json
import java.time.LocalDate

class AstronomyPictureDTO (
    @Json(name = "service_version") val serviceVersion: String,
    val title: String,
    val explanation: String,
    val date: LocalDate,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "hdurl") val hdUrl: String?,
    val url: String?,
)