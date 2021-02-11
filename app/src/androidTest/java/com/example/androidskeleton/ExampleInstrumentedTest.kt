package com.example.androidskeleton

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.assertion.ViewAssertions.matches

import com.example.androidskeleton.ui.MainActivity
import com.example.androidskeleton.ui.MainViewModel
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class ExampleInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var viewModel: MainViewModel

    @Before
    fun setUp(){
      //  hiltRule.inject()
    }

    /*@get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.androidskeleton", appContext.packageName)
    }

    @Test
    fun isUiDisplayed(){
        activityRule.launchActivity(Intent())
        onView(withId(R.id.coordinator)).check(matches(isDisplayed()))
    }*/

    @Test
    fun isHiltWorking(){
       // viewModel.getRecent()
        assertEquals("com.example.androidskeleton", null)
    }
}
