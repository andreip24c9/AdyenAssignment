package com.adyen.android.assignment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.adyen.android.assignment.local.util.LocalDateConverter
import java.time.LocalDate

@Entity
data class AstronomyPicture(
    @PrimaryKey val id: String,
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