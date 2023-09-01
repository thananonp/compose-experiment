package com.thananonp.composeexperiment.allblogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

interface AllBlogsService {
    suspend fun getAllBlogs(): List<Blog>
}

@Parcelize
data class Blog(
    val title: String, val description: String
) : Parcelable
