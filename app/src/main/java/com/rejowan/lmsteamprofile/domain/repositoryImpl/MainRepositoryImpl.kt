package com.rejowan.lmsteamprofile.domain.repositoryImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rejowan.lmsteamprofile.constats.Config
import com.rejowan.lmsteamprofile.data.remote.client.RetrofitClient
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import com.rejowan.lmsteamprofile.domain.repository.MainRepository
import retrofit2.await

class MainRepositoryImpl : MainRepository {


    private val _batterList = MutableLiveData<MutableList<BatterResponse>>()

    override val battersList: LiveData<MutableList<BatterResponse>>
        get() = _batterList

    private val _bowlerList = MutableLiveData<MutableList<BowlerResponse>>()
    override val bowlerList: LiveData<MutableList<BowlerResponse>>
        get() = _bowlerList

    private val _allRounderList = MutableLiveData<MutableList<AllRoundedResponse>>()
    override val allRounderList: LiveData<MutableList<AllRoundedResponse>>
        get() = _allRounderList


    override suspend fun getSummary() {


    }

    override suspend fun getBatters() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getBatterList()?.await()
            response?.let {
                _batterList.postValue(it.toMutableList())
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getBatters: ${e.message}", e)
            _batterList.postValue(mutableListOf())
        }


    }

    override suspend fun getBowlers() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getBowlerList()?.await()
            response?.let {
                _bowlerList.postValue(it.toMutableList())
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getBowlers: ${e.message}", e)
            _bowlerList.postValue(mutableListOf())
        }
    }

    override suspend fun getAllRounders() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getAllRounderList()?.await()
            response?.let {
                _allRounderList.postValue(it.toMutableList())
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getAllRounders: ${e.message}", e)
            _allRounderList.postValue(mutableListOf())
        }
    }


}