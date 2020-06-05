package com.brianckrebs.github.ui.screen.issuelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brianckrebs.github.data.repository.DataRepository

@Suppress("UNCHECKED_CAST")
class IssueListViewModelFactory(
    private val dataRepository: DataRepository,
    private val repoId: Int,
    private val owner: String,
    private val repoName: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(IssueListViewModel::class.java) ->
                IssueListViewModel(dataRepository, repoId, owner, repoName)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}