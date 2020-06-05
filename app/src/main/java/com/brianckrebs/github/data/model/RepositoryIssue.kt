package com.brianckrebs.github.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "RepositoryIssue")
@JsonClass(generateAdapter = true)
data class RepositoryIssue(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Int,

    // foreign key
    @ColumnInfo(name = "repo_id")
    @Transient
    val repoId: Int = -1,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    @Json(name = "body")
    val body: String?,

    @ColumnInfo(name = "state")
    @Json(name = "state")
    val state: String,

    @ColumnInfo(name = "html_url")
    @Json(name = "html_url")
    val url: String,

    @Embedded
    @Json(name = "user")
    val user: RepositoryIssueUser

)