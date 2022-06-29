package com.adyen.android.assignment.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.local.model.AstronomyPictureDAO
import com.adyen.android.assignment.local.util.LocalDateConverter


@Database(entities = [AstronomyPicture::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apodDao(): AstronomyPictureDAO

}