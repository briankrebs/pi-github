package com.brianckrebs.github.data.repository.source.disk

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.data.model.RepositoryIssue

/**
 * Database schema
 */
@Database(
    entities = [Repository::class, RepositoryIssue::class],
    version = 1,
    exportSchema = false
)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract fun repositoryDao(): RepositoryDao
    abstract fun repositoryIssueDao(): RepositoryIssueDao

    companion object {

        fun create(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RepositoryDatabase::class.java, "Github.db"
            ).build()
    }
}
