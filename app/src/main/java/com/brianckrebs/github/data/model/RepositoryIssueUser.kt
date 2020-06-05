package com.brianckrebs.github.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryIssueUser(
    @Json(name = "avatar_url") val avatarUrl: String
)