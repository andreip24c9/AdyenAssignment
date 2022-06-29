package com.adyen.android.assignment

import com.adyen.android.assignment.di.RepositoryModule
import com.adyen.android.assignment.domain.model.AstronomyPicture
import com.adyen.android.assignment.presentation.ui.list.ListFragment
import com.adyen.android.assignment.presentation.ui.list.ListViewModel
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
class ListFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: ListFragment
    private lateinit var viewModel: ListViewModel

    @Inject
    lateinit var planetaryRepository: PlanetaryRepository

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun init() {
        hiltRule.inject()
        launchFragmentInHiltContainer<ListFragment> {
            this@ListFragmentTest.fragment = this as ListFragment
            this@ListFragmentTest.viewModel = fragment.viewModel
        }
    }

    @Test
    fun testInjection() {
        assert(viewModel != null)
    }

    @Test
    fun testApodsList() {
        runBlocking {
            launch {
                async(start = CoroutineStart.LAZY) {
                    assert(viewModel.fetchApods().isEmpty())
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
            return null
        }

        override suspend fun favoriteApod(id: String, shouldFavorite: Boolean): AstronomyPicture? {
            return null
        }

        override suspend fun clearNonFavoriteApods() {
            // do nothing
        }
    }
}