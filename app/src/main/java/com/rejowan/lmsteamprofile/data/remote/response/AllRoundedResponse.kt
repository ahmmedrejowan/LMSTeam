package com.rejowan.lmsteamprofile.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllRoundedResponse(
    @SerializedName("UserId") val userId: Int,

    @SerializedName("UserName") val userName: String,

    @SerializedName("UserPicture") val userPicture: String,

    @SerializedName("PlayerInfo") val playerInfo: String,

    @SerializedName("Innings") val innings: Int,

    @SerializedName("Runs") val runs: Int,

    @SerializedName("BattingAverage") val battingAverage: Double,

    @SerializedName("StrikeRate") val strikeRate: Double,

    @SerializedName("Wickets") val wickets: Int,

    @SerializedName("BowlingAverage") val bowlingAverage: Double,

    @SerializedName("Economy") val economy: Double,

    @SerializedName("WorldRank") val worldRank: Int,

    @SerializedName("NationalRank") val nationalRank: Int,

    @SerializedName("IsFormar") val isFormar: Int
)
