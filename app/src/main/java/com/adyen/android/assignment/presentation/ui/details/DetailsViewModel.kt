package com.adyen.android.assignment.presentation.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.network.util.NoConnectivityException
import com.adyen.android.assignment.presentation.ui.composables.ErrorMessage
import com.adyen.android.assignment.repository.PlanetaryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailsViewModel
@AssistedInject
constructor(
    private val planetaryRepository: PlanetaryRepository,
    @Assisted private val apodId: String?,
) : ViewModel() {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(apodId: String): DetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            apodId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(apodId) as T
            }
        }
    }


    //    var apodId: MutableState<String> = mutableStateOf("")
    val apod: MutableState<AstronomyPicture?> = mutableStateOf(null)

    var listLoader = mutableStateOf(false)
    var initialLoadError: MutableState<ErrorMessage?> = mutableStateOf(null)

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
}