package com.adyen.android.assignment.domain.model

import java.time.LocalDate

data class AstronomyPicture(
    val id: String,
    val title: String,
    val explanation: String,
    val date: LocalDate,
    val mediaType: String,
    val hdUrl: String?,
    val url: String?,
    var favorite: Boolean = false
) {

    enum class MediaType(val value: String) {
        IMAGE("image"),
        VIDEO("video"),
        OTHER("other")
    }
}