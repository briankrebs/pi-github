package com.brianckrebs.github

import android.app.Application
import com.brianckrebs.github.data.repository.DataRepository
import com.brianckrebs.github.data.repository.source.disk.DiskCache
import com.brianckrebs.github.data.repository.source.disk.RepositoryDatabase
import com.brianckrebs.github.data.repository.source.remote.ApiClient

class App : Application() {

    // FIXME - store application dependencies here for now, eventually integrate dagger

    private val apiClient by lazy {
        ApiClient(
            this,
            BuildConfig.API_BASE_URL,
            BuildConfig.DEBUG
        )
    }

    private val database by lazy { RepositoryDatabase.create(this) }
    private val diskCache by lazy {
        DiskCache(
            database.repositoryDao(),
            database.repositoryIssueDao()
        )
    }

    val dataRepository: DataRepository by lazy {
        DataRepository(
            apiClient,
            diskCache
        )
    }
}