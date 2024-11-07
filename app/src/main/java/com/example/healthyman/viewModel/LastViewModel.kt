package com.example.healthyman.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.healthyman.repositories.HomeRepo
import com.example.stress.modal.ExerDetail
import com.example.stress.modal.FoodDetail

class LastViewModel:ViewModel() {

    private val repository= HomeRepo()
    private val _phyExer = MutableLiveData<ExerDetail>()
    val phyExer: LiveData<ExerDetail> = _phyExer


    private val _menExer = MutableLiveData<ExerDetail>()
    val menExer: LiveData<ExerDetail> = _menExer


    private val _vegFood = MutableLiveData<FoodDetail>()
    val vegFood: LiveData<FoodDetail> = _vegFood


    private val _nonVegFood = MutableLiveData<FoodDetail>()
    val nonVegFood: LiveData<FoodDetail> = _nonVegFood


    fun fetchPhyExer(issueName:String,exerciseNumber:String) {
        repository.getphyExer(issueName,exerciseNumber){ phyExer ->
            _phyExer.postValue(phyExer)
        }
    }

    fun fetchMenExer(issueName:String,exerciseNumber:String) {
        repository.getmenExer(issueName,exerciseNumber){ menExer ->
            _menExer.postValue(menExer)
        }
    }

    fun fetchVegFood(issueName:String,foodNumber:String) {
        repository.getfoodsVeg(issueName,foodNumber){ vegfood ->
            _vegFood.postValue(vegfood)
        }
    }

    fun fetchNonVegFood(issueName:String,foodNumber:String) {
        repository.getfoodsNonVeg(issueName,foodNumber){ nonvegfood ->
            _nonVegFood.postValue(nonvegfood)
        }
    }




}