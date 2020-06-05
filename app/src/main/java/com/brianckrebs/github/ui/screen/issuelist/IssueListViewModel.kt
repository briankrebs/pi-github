package com.brianckrebs.github.ui.screen.issuelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.brianckrebs.github.data.model.IssueStateType
import com.brianckrebs.github.data.model.RepositoryIssue
import com.brianckrebs.github.data.repository.DataRepository

class IssueListViewModel(
    private val dataRepository: DataRepository,
    private val repoId: Int,
    private val owner: String,
    private val repoName: String
) : ViewModel() {

    private val _stateFilter = MutableLiveData<IssueStateType>()

    val issues: LiveData<PagedList<RepositoryIssue>> = switchMap(_stateFilter) {
        dataRepository.fetchRepositoryIssues(repoId, owner, repoName, it)
    }

    private fun fetchIssues(stateType: IssueStateType) {
        _stateFilter.postValue(stateType)
    }

    fun setStateFilter(issueStateType: IssueStateType) {
        fetchIssues(issueStateType)
    }
}