package com.rejowan.lmsteamprofile.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeamProfileData(
    val teamInfo: List<TeamInfo>,
    val summaryStats: List<SummaryStats>,
    val rankings: List<Rankings>,
    val topBatsmen: List<Player>,
    val topBowlers: List<Player>,
    val topAllRounders: List<Player>,
    val awards: List<Awards>,
    val matchResults: List<MatchResult>,
    val upcomingFixtures: List<UpcomingFixture>,
    val videos: List<Video>,
    val honors: List<Honor>
)



data class TeamInfo(
    @SerializedName("TeamName") val teamName: String,
    @SerializedName("TeamLogo") val teamLogo: String,
    @SerializedName("SponsorLogo") val sponsorLogo: String,
    @SerializedName("TeamDescription") val teamDescription: String
)

data class SummaryStats(
    @SerializedName("gamesPlayed") val gamesPlayed: Int,
    @SerializedName("WinRatio") val winRatio: Double,
    @SerializedName("Wins") val wins: Int,
    @SerializedName("Loses") val loses: Int
)

data class Rankings(
    @SerializedName("WorldRank") val worldRank: Int,
    @SerializedName("CountryRank") val countryRank: Int,
    @SerializedName("RegionalRank") val regionalRank: Int,
    @SerializedName("Form") val form: String
)

data class Player(
    @SerializedName("UserId") val userId: Int,
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("Nationality") val nationality: Int,
    @SerializedName("UserPicture") val userPicture: String,
    @SerializedName("WorldRank") val worldRank: Int,
    @SerializedName("NationalRank") val nationalRank: Int
)



data class Awards(
    @SerializedName("Champion") val champion: Int,
    @SerializedName("RunnersUp") val runnersUp: Int
)

data class MatchResult(
    @SerializedName("TeamId") val teamId: Int,
    @SerializedName("TeamName") val teamName: String,
    @SerializedName("TeamLogo") val teamLogo: String?,
    @SerializedName("oppoTeamId") val oppoTeamId: Int,
    @SerializedName("oppTeamName") val oppTeamName: String,
    @SerializedName("oppLogo") val oppLogo: String?,
    @SerializedName("MatchInfo") val matchInfo: String,
    @SerializedName("DateTime") val dateTime: String
)

data class UpcomingFixture(
    @SerializedName("TeamId") val teamId: Int,
    @SerializedName("TeamName") val teamName: String,
    @SerializedName("TeamLogo") val teamLogo: String?,
    @SerializedName("oppoTeamId") val oppoTeamId: Int,
    @SerializedName("oppTeamName") val oppTeamName: String,
    @SerializedName("oppLogo") val oppLogo: String?,
    @SerializedName("DateTime") val dateTime: String
)

data class Video(
    @SerializedName("TeamFixture") val teamFixture: Int,
    @SerializedName("Date") val date: String,
    @SerializedName("PlaybackUrl") val playbackUrl: String,
    @SerializedName("YouTube") val youtube: String,
    @SerializedName("FixDate") val fixDate: String,
    @SerializedName("TeamId") val teamId: Int
)

data class Honor(
    @SerializedName("Tournament") val tournament: String,
    @SerializedName("Position") val position: String,
    @SerializedName("WinnigYear") val winningYear: Int
)
