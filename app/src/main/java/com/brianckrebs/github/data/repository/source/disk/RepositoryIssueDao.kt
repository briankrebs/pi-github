package com.brianckrebs.github.data.repository.source.disk

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brianckrebs.github.data.model.RepositoryIssue

/**
 * Room data access object for accessing the [RepositoryIssue] table.
 */
@Dao
interface RepositoryIssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(issues: List<RepositoryIssue>)

    @Query("SELECT * FROM RepositoryIssue WHERE repo_id = :repoId ORDER BY id DESC")
    fun getRepositoryIssues(repoId: Int): DataSource.Factory<Int, RepositoryIssue>

    @Query("SELECT * FROM RepositoryIssue WHERE repo_id = :repoId AND state = :state ORDER BY id DESC")
    fun getRepositoryIssuesByState(
        repoId: Int,
        state: String
    ): DataSource.Factory<Int, RepositoryIssue>
}
