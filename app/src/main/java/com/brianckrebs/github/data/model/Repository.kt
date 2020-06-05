package com.brianckrebs.github.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "Repository")
@JsonClass(generateAdapter = true)
data class Repository(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    @Json(name = "name")
    val name: String,

    @ColumnInfo(name = "open_issues_count")
    @Json(name = "open_issues_count")
    val openIssueCount: Int,

    @Embedded
    @Json(name = "owner") val owner: RepositoryOwner
)