package com.example.healthyman.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthyman.repositories.HomeRepo
import com.example.stress.modal.HomeIssueList

class HomeViewModel: ViewModel() {
    private val repository= HomeRepo()
    private val _homelist = MutableLiveData<List<HomeIssueList>>()
    val homeList: LiveData<List<HomeIssueList>> = _homelist

    fun fetchDisorders() {
        repository.getHomeIssueList { homeList ->
            _homelist.postValue(homeList)
        }
    }
}