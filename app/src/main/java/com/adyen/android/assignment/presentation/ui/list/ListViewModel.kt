package com.adyen.android.assignment.presentation.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.presentation.ui.composables.ErrorMessage
import com.adyen.android.assignment.repository.PlanetaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject
constructor(private val planetaryRepository: PlanetaryRepository) : ViewModel() {

    val apods: MutableState<List<AstronomyPicture>> = mutableStateOf(ArrayList())
    val apodsMap: MutableState<Map<Int, List<AstronomyPicture>>> = mutableStateOf(HashMap())

    var listLoader = mutableStateOf(false)
    var initialLoadError: MutableState<ErrorMessage?> = mutableStateOf(null)

    var fetchApodsJob: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        cancelAllJobs()
        initialLoadError.value = null
        fetchApodsJob = viewModelScope.launch {
            try {
                listLoader.value = true
                apods.value = fetchApods()
                apodsMap.value =
                    fetchApods().groupBy {
                        if (it.favorite) R.string.favorites_header_label
                        else R.string.latest_header_label
                    }
                listLoader.value = false
            } catch (exception: Exception) {
                listLoader.value = false
                initialLoadError.value = ErrorMessage(
                    errorImageRes = R.drawable.ic_vector,
                    titleRes = R.string.unknown_error_title,
                    bodyRes = R.string.unkown_error_body
                )
                cancel()
            }
        }
    }

    suspend fun fetchApods(): List<AstronomyPicture> {
        return planetaryRepository.fetchApodImages(20)
    }

    private fun cancelAllJobs() {
        fetchApodsJob?.cancel()
    }
}