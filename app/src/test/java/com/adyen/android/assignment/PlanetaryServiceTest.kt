package com.adyen.android.assignment

import com.adyen.android.assignment.di.NetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


class PlanetaryServiceTest {

    /**
     * Integration test -
     * ensures the [generated key](https://api.nasa.gov/) returns results from the api
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testResponseCode() = runTest {
        val apiService = NetworkModule.provideApiService(NetworkModule.provideConverterFactory())
        val response = apiService.fetchApods(2, BuildConfig.API_KEY)
        assert(response.isNotEmpty())
    }
}
