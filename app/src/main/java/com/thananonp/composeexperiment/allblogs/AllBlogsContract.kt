package com.thananonp.composeexperiment.allblogs

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.jvm.Throws

interface AllBlogsService {
    @Throws
    suspend fun getAllBlogs(): List<Blog>
}

@Parcelize
data class Blog(
    val title: String, val description: String
) : Parcelable
