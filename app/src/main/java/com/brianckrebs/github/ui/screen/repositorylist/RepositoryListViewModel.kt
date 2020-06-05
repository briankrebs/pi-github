package com.brianckrebs.github.ui.screen.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.brianckrebs.github.data.model.OrganizationType
import com.brianckrebs.github.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers

class RepositoryListViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val repositoryList = liveData(Dispatchers.IO) {
        val repositories = dataRepository.fetchRepositories(OrganizationType.GOOGLE)
        emit(repositories)
    }
}