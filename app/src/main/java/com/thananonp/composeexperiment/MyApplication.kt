package com.thananonp.composeexperiment

import android.app.Application
import com.thananonp.composeexperiment.allblogs.AllBlogsService
import com.thananonp.composeexperiment.allblogs.MockAllBlogsService

class MyApplication : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl()
    }
}


interface AppModule {
    val allBlogsService: AllBlogsService
}

class AppModuleImpl : AppModule {
    override val allBlogsService: AllBlogsService by lazy { MockAllBlogsService() }
}