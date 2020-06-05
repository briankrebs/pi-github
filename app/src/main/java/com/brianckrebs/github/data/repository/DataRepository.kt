package com.brianckrebs.github.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.brianckrebs.github.data.model.IssueStateType
import com.brianckrebs.github.data.model.OrganizationType
import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.data.model.RepositoryIssue
import com.brianckrebs.github.data.repository.source.disk.DiskCache
import com.brianckrebs.github.data.repository.source.remote.ApiClient

class DataRepository(
    private val apiClient: ApiClient,
    private val diskCache: DiskCache
) {

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }

    suspend fun fetchRepositories(organization: OrganizationType): List<Repository> {
        val cachedRepos = diskCache.getRepositories()
        return if (cachedRepos.isNullOrEmpty()) {
            val remoteRepos = apiClient.fetchOrganizationRepositories(organization.name)
            diskCache.insertRepositories(remoteRepos)
            diskCache.getRepositories()
        } else {
            cachedRepos
        }
    }

    fun fetchRepositoryIssues(
        repoId: Int,
        owner: String,
        repoName: String,
        state: IssueStateType
    ): LiveData<PagedList<RepositoryIssue>> {

        val dataSourceFactory = diskCache.getRepositoryIssues(repoId, state)
        val boundaryCallback = RepositoryIssueBoundaryCallback(
            apiClient, diskCache, repoId, owner, repoName, state
        )
        val config = PagedList.Config.Builder()
            .setPageSize(DATABASE_PAGE_SIZE)
            .setInitialLoadSizeHint(50)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(true)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }
}