package com.thananonp.composeexperiment.allblogs

import kotlinx.coroutines.delay

class MockAllBlogsService : AllBlogsService {
    override suspend fun getAllBlogs(): List<Blog> {
        delay(1_500)
        return listOf(
            Blog("Blog 1", "This is blog number 1"),
            Blog("Blog 2", "This is blog number 2"),
            Blog("Blog 3", "This is blog number 3"),
            Blog("Blog 4", "This is blog number 4")
        )

    }
}