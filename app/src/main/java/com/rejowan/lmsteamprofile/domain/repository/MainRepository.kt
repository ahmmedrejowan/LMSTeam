package com.rejowan.lmsteamprofile.domain.repository

import androidx.lifecycle.LiveData
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse

interface MainRepository {

    val battersList: LiveData<MutableList<BatterResponse>>

    val bowlerList: LiveData<MutableList<BowlerResponse>>

    val allRounderList: LiveData<MutableList<AllRoundedResponse>>


    suspend fun getSummary()

    suspend fun getBatters()

    suspend fun getBowlers()

    suspend fun getAllRounders()


}