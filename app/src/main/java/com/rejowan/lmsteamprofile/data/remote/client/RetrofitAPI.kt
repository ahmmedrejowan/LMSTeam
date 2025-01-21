package com.rejowan.lmsteamprofile.data.remote.client

import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {


    // Summary
    @GET("GetWorldTeamProfileSummery?teamId=7027")
    fun getSummary(): Call<Any>

    // All-rounder list
    @GET("GetWorldTeamProfile?typeId=4&teamId=7027")
    fun getAllRounderList(): Call<List<AllRoundedResponse>>

    // Batter list
    @GET("GetWorldTeamProfile?typeId=1&teamId=7027")
    fun getBatterList(): Call<List<BatterResponse>>

    // Bowler list
    @GET("GetWorldTeamProfile?typeId=2&teamId=7027")
    fun getBowlerList(): Call<List<BowlerResponse>>


}