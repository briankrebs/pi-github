package com.brianckrebs.github.data.repository.source.remote

import android.content.Context
import com.brianckrebs.github.data.model.Repository
import com.brianckrebs.github.data.model.RepositoryIssue
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(
    context: Context,
    baseUrl: String,
    isDebug: Boolean
) {

    private val api: Api

    init {
        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        clientBuilder.readTimeout(60, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(60, TimeUnit.SECONDS)

        if (isDebug) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(loggingInterceptor)

            Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                    .build()
            )
            clientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuilder.build())
            .build()

        api = retrofit.create(Api::class.java)
    }

    suspend fun fetchOrganizationRepositories(organizationName: String): List<Repository> {
        return api.fetchOrganizationRepositories(organizationName)
    }

    suspend fun fetchRepositoryIssues(
        owner: String,
        repoName: String,
        state: String,
        page: Int,
        perPage: Int
    ): List<RepositoryIssue> {
        return api.fetchRepositoryIssues(owner, repoName, state, page, perPage)
    }
}