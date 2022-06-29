package com.adyen.android.assignment

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.adyen.android.assignment.di.RepositoryModule
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.presentation.ui.details.DetailsFragment
import com.adyen.android.assignment.presentation.ui.details.DetailsViewModel
import com.adyen.android.assignment.repository.PlanetaryRepository
import com.adyen.android.assignment.util.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(RepositoryModule::class)
@HiltAndroidTest
class DetailsFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    private lateinit var fragment: DetailsFragment
    private lateinit var viewModel: DetailsViewModel

    @Inject
    lateinit var planetaryRepository: PlanetaryRepository

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<DetailsFragment> {
            this@DetailsFragmentTest.fragment = this as DetailsFragment
            this@DetailsFragmentTest.viewModel = fragment.viewModel
        }
    }

    @Test
    fun testWhenFragmentStarts() {
        assert(viewModel.apod.value == null)
    }

    @Test
    fun testNoId() {
        assert(viewModel.apodId == null)
    }

    @Test
    fun testApodDetailsNull() {
        runBlocking {
            launch {
                async(start = CoroutineStart.LAZY) {
                    viewModel.apodId = null
                    assert(viewModel.fetchApodDetails() == null)
                }.start()
            }
        }
    }

    @Test
    fun testApodDetailsNotNull() {
        runBlocking {
            launch {
                async(start = CoroutineStart.LAZY) {
                    viewModel.apodId = "1"
                    assert(viewModel.fetchApodDetails() != null)
                }.start()
            }
        }
    }

    @Test
    fun testApodDetails() {
        runBlocking {
            launch {
                async(start = CoroutineStart.LAZY) {
                    viewModel.apodId = "1"
                    assert(viewModel.fetchApodDetails()?.title == "Mock title")
                }.start()
            }
        }
    }

    @Test
    fun testFavoriteApod() {
        runBlocking {
            launch {
                async(start = CoroutineStart.LAZY) {
                    assert(planetaryRepository.favoriteApod("1", true)?.favorite == true)
                }.start()
            }
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object MockRepositoryModule {

        @Singleton
        @Provides
        fun providesPlanetaryRepository(): PlanetaryRepository {
            return MockPlanetaryRepository()
        }
    }

    class MockPlanetaryRepository : PlanetaryRepository {
        override suspend fun fetchApodImages(): List<AstronomyPicture> {
            return mutableListOf()
        }

        override suspend fun fetchApodDetails(id: String): AstronomyPicture? {
            return AstronomyPicture(
                "1",
                "Mock title",
                "mock explanation",
                LocalDate.now(),
                "image",
                null,
                null,
                false
            )
        }

        override suspend fun favoriteApod(id: String, shouldFavorite: Boolean): AstronomyPicture? {
            return AstronomyPicture(
                "1",
                "Mock title fav",
                "mock explanation fav",
                LocalDate.now(),
                "image",
                null,
                null,
                true
            )
        }

        override suspend fun clearNonFavoriteApods() {
            // do nothing
        }
    }
}