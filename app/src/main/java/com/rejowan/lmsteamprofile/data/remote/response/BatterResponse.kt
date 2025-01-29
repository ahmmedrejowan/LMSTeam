package com.rejowan.lmsteamprofile.data.remote.response

import com.google.gson.annotations.SerializedName

data class BatterResponse(
    @SerializedName("UserId") val userId: Int?,

    @SerializedName("FirstName") val firstName: String?,

    @SerializedName("LastName") val lastName: String?,

    @SerializedName("UserPicture") val userPicture: String?,

    @SerializedName("PlayerInfo") val playerInfo: String?,

    @SerializedName("Innings") val innings: Int?,

    @SerializedName("Runs") val runs: Int?,

    @SerializedName("Average") val average: Double?,

    @SerializedName("StrikeRate") val strikeRate: Double?,

    @SerializedName("HighestScore") val highestScore: Int?,

    @SerializedName("Fifties") val fifties: Int?,

    @SerializedName("Hundred") val hundred: Int?,

    @SerializedName("WorldRank") val worldRank: Int?,

    @SerializedName("NationalRank") val nationalRank: Int?,

    @SerializedName("IsFormar") val isFormar: Int?
)
