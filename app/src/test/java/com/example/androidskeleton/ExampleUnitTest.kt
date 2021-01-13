package com.example.androidskeleton

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.StatefulData
import com.example.androidskeleton.data.model.search.Repo
import com.example.androidskeleton.data.repository.MainRepository
import com.example.androidskeleton.di.appModule
import com.example.androidskeleton.di.dbModule
import com.example.androidskeleton.di.networkingModule
import com.example.androidskeleton.ui.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : KoinTest {
    @get:Rule
    val testInstantExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    val testLifecycleOwner = LifeCycleTestOwner()

    @Before
    fun setup() {
        startKoin {
            androidContext(Mockito.mock(Context::class.java))
            modules(
                networkingModule,
                appModule,
                dbModule
            )
        }
    }

    @Test
    fun testViewModel() {
        testCoroutineRule.testDispatcher.runBlockingTest {
            val viewModel by inject<MainViewModel>()

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
        stopKoin()
    }
}
