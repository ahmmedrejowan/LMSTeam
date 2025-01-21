package com.rejowan.lmsteamprofile.domain.repositoryImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.rejowan.lmsteamprofile.constats.Config
import com.rejowan.lmsteamprofile.data.remote.client.RetrofitClient
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.Awards
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import com.rejowan.lmsteamprofile.data.remote.response.Honor
import com.rejowan.lmsteamprofile.data.remote.response.MatchResult
import com.rejowan.lmsteamprofile.data.remote.response.Player
import com.rejowan.lmsteamprofile.data.remote.response.Rankings
import com.rejowan.lmsteamprofile.data.remote.response.SummaryStats
import com.rejowan.lmsteamprofile.data.remote.response.TeamInfo
import com.rejowan.lmsteamprofile.data.remote.response.TeamProfileData
import com.rejowan.lmsteamprofile.data.remote.response.UpcomingFixture
import com.rejowan.lmsteamprofile.data.remote.response.Video
import com.rejowan.lmsteamprofile.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class MainRepositoryImpl : MainRepository {

    private val _teamProfileData = MutableLiveData<TeamProfileData>()
    override val teamProfileData: LiveData<TeamProfileData>
        get() = _teamProfileData

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
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getSummary()?.await()

            response?.let {
                val gson = Gson()

                // Parse index 0 - Team Info
                val teamInfoList = gson.fromJson(
                    gson.toJson(it[0]), Array<TeamInfo>::class.java
                ).toList()

                // Parse index 1 - Summary Stats
                val summaryStatsList = gson.fromJson(
                    gson.toJson(it[1]), Array<SummaryStats>::class.java
                ).toList()

                // Parse index 2 - Rankings
                val rankingsList = gson.fromJson(
                    gson.toJson(it[2]), Array<Rankings>::class.java
                ).toList()

                // Parse index 3 - Top Players (Batsmen)
                val topBatsmenList = gson.fromJson(
                    gson.toJson(it[3]), Array<Player>::class.java
                ).toList()

                // Parse index 4 - Top Players (Bowlers)
                val topBowlersList = gson.fromJson(
                    gson.toJson(it[4]), Array<Player>::class.java
                ).toList()

                // Parse index 5 - Top Players (All-Rounders)
                val topAllRoundersList = gson.fromJson(
                    gson.toJson(it[5]), Array<Player>::class.java
                ).toList()

                // Parse index 6 - Awards
                val awardsList = gson.fromJson(
                    gson.toJson(it[6]), Array<Awards>::class.java
                ).toList()

                // Parse index 7 - Match Results
                val matchResultsList = gson.fromJson(
                    gson.toJson(it[7]), Array<MatchResult>::class.java
                ).toList()

                // Parse index 8 - Upcoming Fixtures
                val upcomingFixturesList = gson.fromJson(
                    gson.toJson(it[8]), Array<UpcomingFixture>::class.java
                ).toList()

                // Parse index 9 - Videos
                val videosList = gson.fromJson(
                    gson.toJson(it[9]), Array<Video>::class.java
                ).toList()

                // Parse index 10 - Honors
                val honorsList = gson.fromJson(
                    gson.toJson(it[10]), Array<Honor>::class.java
                ).toList()


                val teamProfileData = TeamProfileData(
                    teamInfo = teamInfoList,
                    summaryStats = summaryStatsList,
                    rankings = rankingsList,
                    topBatsmen = topBatsmenList,
                    topBowlers = topBowlersList,
                    topAllRounders = topAllRoundersList,
                    awards = awardsList,
                    matchResults = matchResultsList,
                    upcomingFixtures = upcomingFixturesList,
                    videos = videosList,
                    honors = honorsList
                )

                withContext(Dispatchers.Main) {

                    _teamProfileData.postValue(teamProfileData)
                }
            }


        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getSummary: ${e.message}", e)
        }

    }

    override suspend fun getBatters() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getBatterList()?.await()
            response?.let {
                withContext(Dispatchers.Main) {
                    _batterList.postValue(it.toMutableList())
                }
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getBatters: ${e.message}", e)
        }


    }

    override suspend fun getBowlers() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getBowlerList()?.await()
            response?.let {
                withContext(Dispatchers.Main) {
                    _bowlerList.postValue(it.toMutableList())
                }
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getBowlers: ${e.message}", e)
        }
    }

    override suspend fun getAllRounders() {
        try {
            val response = RetrofitClient.getInstance(Config.BASE_URL)?.getAllRounderList()?.await()
            response?.let {
                withContext(Dispatchers.Main) {
                    _allRounderList.postValue(it.toMutableList())
                }
            }
        } catch (e: Exception) {
            Log.e("MainRepositoryImpl", "getAllRounders: ${e.message}", e)
        }
    }


}