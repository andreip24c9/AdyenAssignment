package com.adyen.android.assignment

import androidx.fragment.app.ListFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.launchActivity
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.adyen.android.assignment.di.AppModule
import com.adyen.android.assignment.presentation.MainActivity
import com.adyen.android.assignment.presentation.ui.details.DetailsFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.hamcrest.CoreMatchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(AppModule::class)
@HiltAndroidTest
class HiltTestingPlayground {

    @Inject
    lateinit var someString: String

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun someTest() {
        // checking if the @UninstallModules annotation works properly
        assertThat(someString, containsString("TESTING"))
    }

    @Test
    fun mainActivityTest() {
        val scenario = launchActivity<MainActivity>()
    }

    @Test
    fun listFragmentTest() {
        val scenario = launchFragmentInContainer<ListFragment>()
    }

    @Test
    fun detailsFragmentTest() {
        val scenario = launchFragmentInContainer<DetailsFragment>()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Singleton
        @Provides
        fun provideSomeString() : String {
            return "It's some TESTING string"
        }
    }
}