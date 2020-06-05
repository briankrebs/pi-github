package com.brianckrebs.github.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.brianckrebs.github.data.model.IssueStateType
import com.brianckrebs.github.data.model.RepositoryIssue
import com.brianckrebs.github.data.repository.source.disk.DiskCache
import com.brianckrebs.github.data.repository.source.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This boundary callback gets notified when user reaches to the edges of the list for example when
 * the database cannot provide any more data.
 **/
class RepositoryIssueBoundaryCallback(
    private val apiClient: ApiClient,
    private val diskCache: DiskCache,
    private val repoId: Int,
    private val owner: String,
    private val repoName: String,
    private val state: IssueStateType
) : PagedList.BoundaryCallback<RepositoryIssue>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d(RepositoryIssueBoundaryCallback::class.java.canonicalName, "onZeroItemsLoaded")
        GlobalScope.launch(Dispatchers.IO) {
            requestAndSaveData(repoId, owner, repoName, state)
        }
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: RepositoryIssue) {
        Log.d(RepositoryIssueBoundaryCallback::class.java.canonicalName, "onItemAtEndLoaded")
        GlobalScope.launch(Dispatchers.IO) {
            requestAndSaveData(repoId, owner, repoName, state)
        }
    }

    private suspend fun requestAndSaveData(
        repoId: Int,
        owner: String,
        repoName: String,
        state: IssueStateType
    ) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        val response = apiClient.fetchRepositoryIssues(
            owner,
            repoName,
            state.value,
            lastRequestedPage,
            NETWORK_PAGE_SIZE
        )

        // on success
        val mappedResponse = response.map { it.copy(repoId = repoId) }
        diskCache.insertIssues(mappedResponse)
        lastRequestedPage++
        isRequestInProgress = false

        // TODO - add network error check
//        _networkErrors.postValue(error)
//        isRequestInProgress = false
    }
}