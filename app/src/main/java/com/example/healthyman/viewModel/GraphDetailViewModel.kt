package com.example.healthyman.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthyman.repositories.HomeRepo
import com.example.stress.modal.GraphDetails

class GraphDetailViewModel: ViewModel() {

    private val repository= HomeRepo()
    private val _graphDetails = MutableLiveData<GraphDetails>()
    val graphDetails: LiveData<GraphDetails> = _graphDetails

    fun fetchGraphDetails(disorderName: String) {
        repository.getGraphDetails(disorderName) { details ->
            details?.let {
                // Include the disorder name in the details
//                val updatedDetails = it.copy(name = disorderName)
                _graphDetails.postValue(it.copy())
            }
        }
    }
}