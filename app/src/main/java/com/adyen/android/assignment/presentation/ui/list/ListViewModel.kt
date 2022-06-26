package com.adyen.android.assignment.presentation.ui.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.R
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.network.util.NoConnectivityException
import com.adyen.android.assignment.presentation.ui.composables.ErrorMessage
import com.adyen.android.assignment.repository.PlanetaryRepository
import com.adyen.android.assignment.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ListViewModel
@Inject
constructor(
    private val planetaryRepository: PlanetaryRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val apodsMap: MutableState<Map<Int, List<AstronomyPicture>>> = mutableStateOf(HashMap())
    val selectedSorting : MutableState<Sorting> = mutableStateOf(Sorting.TITLE)

    var showSortingButton = mutableStateOf(false)
    var listLoader = mutableStateOf(false)
    var initialLoadError: MutableState<ErrorMessage?> = mutableStateOf(null)

    var fetchApodsJob: Job? = null

    enum class Sorting(val value: String) {
        TITLE("title"),
        DATE("date");

        companion object {
            fun fromValue(value: String?) = values().find { it.value == value } ?: TITLE
        }
    }

    init {
        refresh()
    }

    fun refresh() {
        cancelAllJobs()
        initialLoadError.value = null
        fetchApodsJob = viewModelScope.launch {
            try {
                listLoader.value = true
                val responseMap = fetchApods().groupBy {
                    if (it.favorite) R.string.favorites_header_label
                    else R.string.latest_header_label
                }
                selectedSorting.value = getSortingParam()
                when (selectedSorting.value) {
                    Sorting.TITLE -> responseMap.sortByTitle()
                    Sorting.DATE -> responseMap.sortByDate()
                }
                apodsMap.value = responseMap
                listLoader.value = false
                showSortingButton.value = true
            } catch (exception: Exception) {
                apodsMap.value = mutableMapOf()
                listLoader.value = false
                showSortingButton.value = false
                if(exception is NoConnectivityException) {
                    initialLoadError.value = ErrorMessage(
                        errorImageRes = R.drawable.ic_no_network,
                        titleRes = R.string.network_error_title,
                        bodyRes = R.string.network_error_body
                    )
                } else {
                    initialLoadError.value = ErrorMessage(
                        errorImageRes = R.drawable.ic_error,
                        titleRes = R.string.unknown_error_title,
                        bodyRes = R.string.unkown_error_body
                    )
                }
                cancel()
            }
        }
    }

    suspend fun fetchApods(): List<AstronomyPicture> {
        return planetaryRepository.fetchApodImages(20)
    }

    private fun getSortingParam(): Sorting {
        return Sorting.fromValue(settingsRepository.getPlanetarySorting())
    }

    private fun cancelAllJobs() {
        fetchApodsJob?.cancel()
    }

    fun reorderByTitle() {
        selectedSorting.value = Sorting.TITLE
        settingsRepository.setPlanetarySorting(Sorting.TITLE.value)
        apodsMap.value.sortByTitle()
    }

    fun reorderByDate() {
        selectedSorting.value = Sorting.DATE
        settingsRepository.setPlanetarySorting(Sorting.DATE.value)
        apodsMap.value.sortByDate()
    }
}

private fun Map<Int, List<AstronomyPicture>>.sortByTitle(): Map<Int, List<AstronomyPicture>> {
    val map = this as MutableMap
    map.forEach { (key, value) ->
        map[key] = value.sortedBy { it.title }
    }
    return map
}

private fun Map<Int, List<AstronomyPicture>>.sortByDate(): Map<Int, List<AstronomyPicture>> {
    val map = this as MutableMap
    map.forEach { (key, value) ->
        map[key] = value.sortedByDescending { it.date }
    }
    return map
}