package com.rejowan.lmsteamprofile.data.remote.response

import com.google.gson.annotations.SerializedName

data class BowlerResponse(
    @SerializedName("UserId") val userId: Int?,

    @SerializedName("FirstName") val firstName: String?,

    @SerializedName("LastName") val lastName: String?,

    @SerializedName("UserPicture") val userPicture: String?,

    @SerializedName("PlayerInfo") val playerInfo: String?,

    @SerializedName("Overs") val overs: Double?,

    @SerializedName("Wickets") val wickets: Int?,

    @SerializedName("Average") val average: Double?,

    @SerializedName("Economy") val economy: Double?,

    @SerializedName("Best") val best: String?,

    @SerializedName("ThreeFA") val threeFa: Int?,

    @SerializedName("WorldRank") val worldRank: Int?,

    @SerializedName("NationalRank") val nationalRank: Int?,

    @SerializedName("IsFormar") val isFormar: Int?
)