package com.adyen.android.assignment.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adyen.android.assignment.domain.model.AstronomyPicture

@Dao
interface AstronomyPictureDAO {

    @Query("SELECT * FROM AstronomyPicture")
    suspend fun getApodList(): List<AstronomyPicture>

    @Query("SELECT * FROM AstronomyPicture WHERE id = :apodId")
    suspend fun getApod(apodId: String) : AstronomyPicture

    @Query("UPDATE AstronomyPicture SET favorite = :favorite WHERE id = :apodId")
    suspend fun updateApodFavorite(apodId: String, favorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApods(apods:List<AstronomyPicture>)

    @Query("DELETE FROM AstronomyPicture WHERE favorite = 0")
    suspend fun deleteNonFavoriteApods()
}