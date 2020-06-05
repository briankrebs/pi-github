package com.brianckrebs.github.data.repository.source.disk

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brianckrebs.github.data.model.Repository

/**
 * Room data access object for accessing the [Repository] table.
 */
@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(issues: List<Repository>)

    @Query("SELECT * FROM Repository")
    suspend fun getRepositories(): List<Repository>
}
