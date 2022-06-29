package com.adyen.android.assignment

import com.adyen.android.assignment.network.ApiService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class PlanetaryServiceTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var apiService: ApiService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testPlanetaryService() {
        runBlocking {
            launch {
                val coroutine = async(start = CoroutineStart.LAZY) {
                    val response = apiService.fetchApods(2, BuildConfig.API_KEY)
                    assert(response.size == 2)
                }
                coroutine.start()
            }
        }
    }
}