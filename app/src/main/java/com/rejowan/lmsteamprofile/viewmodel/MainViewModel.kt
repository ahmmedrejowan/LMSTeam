package com.rejowan.lmsteamprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rejowan.lmsteamprofile.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {


    val teamProfileData = repository.teamProfileData
    val battersList = repository.battersList
    val bowlerList = repository.bowlerList
    val allRounderList = repository.allRounderList


    fun getSummary() {
        viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
            repository.getSummary()
        }
    }

    fun getBatters() {
        viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
            repository.getBatters()
        }
    }

    fun getBowlers() {
        viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
            repository.getBowlers()
        }
    }

    fun getAllRounders() {
        viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
            repository.getAllRounders()
        }
    }


}

