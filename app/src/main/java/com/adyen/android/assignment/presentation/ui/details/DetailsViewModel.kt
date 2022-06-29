package com.adyen.android.assignment.presentation.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.network.util.NoConnectivityException
import com.adyen.android.assignment.presentation.ui.composables.ErrorMessage
import com.adyen.android.assignment.repository.PlanetaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject
constructor(
    private val planetaryRepository: PlanetaryRepository,
    private val state: SavedStateHandle,
) : ViewModel() {

    val apodId = state.get<String>("apod_id")
    val apod = mutableStateOf<AstronomyPicture?>(null)
    val isFavorite = mutableStateOf(false)

    var listLoader = mutableStateOf(false)
    var initialLoadError = mutableStateOf<ErrorMessage?>(null)

    private var fetchApodDetailsJob: Job? = null

    init {
        refresh()
    }

    private fun refresh() {
        cancelAllJobs()
        initialLoadError.value = null
        fetchApodDetailsJob = viewModelScope.launch {
            try {
                listLoader.value = true
                apod.value = fetchApodDetails()
                isFavorite.value = apod.value?.favorite ?: false
                listLoader.value = false
                if (apod.value == null) {
                    initialLoadError.value = ErrorMessage(
                        errorImageRes = R.drawable.ic_error,
                        titleRes = R.string.unknown_error_title,
                        bodyRes = R.string.unkown_adpod_details_error_body
                    )
                }
            } catch (exception: Exception) {
                apod.value = null
                listLoader.value = false
                if (exception is NoConnectivityException) {
                    initialLoadError.value = ErrorMessage(
                        errorImageRes = R.drawable.ic_no_network,
                        titleRes = R.string.network_error_title,
                        bodyRes = R.string.network_error_body
                    )
                } else {
                    initialLoadError.value = ErrorMessage(
                        errorImageRes = R.drawable.ic_error,
                        titleRes = R.string.unknown_error_title,
                        bodyRes = R.string.unkown_adpod_details_error_body
                    )
                }
                cancel()
            }
        }
    }

    private suspend fun fetchApodDetails(): AstronomyPicture? {
        return apodId?.let { planetaryRepository.fetchApodDetails(apodId) }
    }

    private fun cancelAllJobs() {
        fetchApodDetailsJob?.cancel()
    }

    fun onLikeClicked() {
        viewModelScope.launch {
            apodId?.let {
                isFavorite.value = !isFavorite.value
                apod.value = planetaryRepository.favoriteApod(apodId, isFavorite.value)
            }
        }


//        if (apod.value != null) {
//            val id = apod.value!!.id
//            val currentFavValue = apod.value!!.favorite
//            isFavorite.value = !isFavorite.value
//            apod.value = planetaryRepository.favoriteApod(id, !isFavorite.value)
//        }

//        apod.value?.let {
//            apod.value = null
//            listLoader.value = true
//            initialLoadError.value = null
////            val apodTemp = planetaryRepository.favoriteApod(it.id, !it.favorite)
////            apod.value = apodTemp
//        }
    }
}