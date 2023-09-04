package com.thananonp.composeexperiment

import com.thananonp.composeexperiment.allblogs.AllBlogsService
import com.thananonp.composeexperiment.allblogs.AllBlogsViewDelegate
import com.thananonp.composeexperiment.allblogs.AllBlogsViewModel
import com.thananonp.composeexperiment.allblogs.Blog
import com.thananonp.composeexperiment.allblogs.MockAllBlogsService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertSame
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.lang.Exception

class ViewModelTest {
    private var sut: AllBlogsViewModel? = null

    @Before
    fun init() {
        sut = AllBlogsViewModel(
            service = MockAllBlogsService(), delegate = MockAllBlogsViewModelDelegate()
        )
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun onInit_state_error() {
        assertNull(sut?.error)
    }

    @Test
    fun onInit_state_isLoading() {
        assertFalse(sut!!.isLoading)
    }

    @Test
    fun onInit_state_data() {
        assertEquals(sut!!.data, listOf<Blog>())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fun_getAllBlogs() = runTest {
        val expected = listOf(
            Blog("Blog 1", "This is blog number 1"),
            Blog("Blog 2", "This is blog number 2"),
            Blog("Blog 3", "This is blog number 3"),
            Blog("Blog 4", "This is blog number 4")
        )

        sut!!.getAllBlogs()
        advanceUntilIdle() // Yields to perform the registrations
        assertEquals(expected, sut!!.data)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fun_getAllBlogs_error() = runTest {
        val sut = AllBlogsViewModel(
            service = ErrorMockAllBlogsService(), delegate = MockAllBlogsViewModelDelegate()
        )
        val expected = Error("EEEEE")

        try {
            sut.getAllBlogs()
            advanceUntilIdle() // Yields to perform the registrations
            assertFalse(sut.isLoading)
            assertTrue(expected === sut.error)
        } catch (_: Error){
        }
    }
}


@ExperimentalCoroutinesApi
class MainDispatcherRule(val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description?) = Dispatchers.resetMain()

}

class MockAllBlogsViewModelDelegate : AllBlogsViewDelegate {
    var receivedBlog: Blog? = null
    override fun didClickBlog(blog: Blog) {
        receivedBlog = blog
    }
}

class ErrorMockAllBlogsService : AllBlogsService {
    override suspend fun getAllBlogs(): List<Blog> {
        throw Error("EEEEE")
    }
}