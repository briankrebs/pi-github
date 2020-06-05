package com.brianckrebs.github.data.repository.source.remote

import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.data.model.RepositoryIssue
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("/orgs/{org}/repos")
    suspend fun fetchOrganizationRepositories(
        @Path("org") organizationName: String
    ): List<Repository>

    @GET("/repos/{owner}/{repoName}/issues")
    suspend fun fetchRepositoryIssues(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Query("state") state: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepositoryIssue>

}