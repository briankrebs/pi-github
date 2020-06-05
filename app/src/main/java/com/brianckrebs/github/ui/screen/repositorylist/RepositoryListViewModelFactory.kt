package com.brianckrebs.github.ui.screen.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.brianckrebs.github.data.repository.DataRepository

@Suppress("UNCHECKED_CAST")
class RepositoryListViewModelFactory(
    private val dataRepository: DataRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(RepositoryListViewModel::class.java) ->
                RepositoryListViewModel(
                    dataRepository
                )
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}