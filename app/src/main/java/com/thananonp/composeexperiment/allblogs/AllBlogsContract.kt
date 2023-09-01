package com.thananonp.composeexperiment.allblogs

interface AllBlogsService {
    suspend fun getAllBlogs(): List<Blog>
}

data class Blog(
    val title: String, val description: String
)
