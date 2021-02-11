package com.example.androidskeleton

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.StatefulData
import com.example.androidskeleton.data.model.search.Repo
import com.example.androidskeleton.ui.MainViewModel
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class ExampleUnitTest {
    @get:Rule
    val testInstantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    val testLifecycleOwner = LifeCycleTestOwner()

    @Inject
    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {

    }

    @Test
    fun testViewModel() {
        testCoroutineRule.testDispatcher.runBlockingTest {

            var response: StatefulData<ApiResponse<List<Repo>>>? = null

            viewModel.getRepoListLiveData().observeForever {
                response = it
            }
            viewModel.searchRepo("Android")

            assertEquals(StatefulData.loading<ApiResponse<List<Repo>>>(), response)
        }
    }

    @After
    fun tearDown() {

    }
}
