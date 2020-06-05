package com.brianckrebs.github.data.repository.source.disk

import androidx.paging.DataSource
import com.brianckrebs.github.data.model.IssueStateType
import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.data.model.RepositoryIssue

class DiskCache(
    private val repositoryDao: RepositoryDao,
    private val repositoryIssueDao: RepositoryIssueDao
) {
    suspend fun insertRepositories(repositories: List<Repository>) {
        repositoryDao.insert(repositories)
    }

    suspend fun insertIssues(issues: List<RepositoryIssue>) {
        repositoryIssueDao.insert(issues)
    }

    suspend fun getRepositories(): List<Repository> {
        return repositoryDao.getRepositories()
    }

    fun getRepositoryIssues(
        repoId: Int,
        state: IssueStateType
    ): DataSource.Factory<Int, RepositoryIssue> {
        return if (state == IssueStateType.ALL) {
            repositoryIssueDao.getRepositoryIssues(repoId)
        } else {
            repositoryIssueDao.getRepositoryIssuesByState(repoId, state.value)
        }
    }
}