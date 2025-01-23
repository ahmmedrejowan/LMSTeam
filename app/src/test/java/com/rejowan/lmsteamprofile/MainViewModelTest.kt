package com.rejowan.lmsteamprofile


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import com.rejowan.lmsteamprofile.data.remote.response.Rankings
import com.rejowan.lmsteamprofile.data.remote.response.SummaryStats
import com.rejowan.lmsteamprofile.data.remote.response.TeamInfo
import com.rejowan.lmsteamprofile.data.remote.response.TeamProfileData
import com.rejowan.lmsteamprofile.domain.repository.MainRepository
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class MainViewModelTest : KoinTest {

    private val testModule = module {
        single<MainRepository> { mock(MainRepository::class.java) }
        factory { MainViewModel(get()) }
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: MainRepository by inject()
    private val viewModel: MainViewModel by inject()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `verify getSummary calls repository`() = runTest(testDispatcher) {
        viewModel.getSummary()

        verify(repository).getSummary()
    }

    @Test
    fun `verify getBatters calls repository`() = runTest(testDispatcher) {
        viewModel.getBatters()

        verify(repository).getBatters()
    }

    @Test
    fun `verify getBowlers calls repository`() = runTest(testDispatcher) {
        viewModel.getBowlers()

        verify(repository).getBowlers()
    }

    @Test
    fun `verify getAllRounders calls repository`() = runTest(testDispatcher) {
        viewModel.getAllRounders()

        verify(repository).getAllRounders()
    }

    @Test
    fun `verify getAllRounders updates LiveData correctly`() = runTest(testDispatcher) {
        // Arrange
        val mockAllRounders = mutableListOf(
            AllRoundedResponse(
                userId = 1,
                userName = "AllRounder 1",
                userPicture = "url1",
                playerInfo = "Player Info 1",
                innings = 20,
                runs = 500,
                battingAverage = 50.0,
                strikeRate = 120.0,
                wickets = 25,
                bowlingAverage = 20.0,
                economy = 4.5,
                worldRank = 1,
                nationalRank = 1,
                isFormar = 0
            )
        )
        val mockLiveData = MutableLiveData<MutableList<AllRoundedResponse>>().apply {
            value = mockAllRounders
        }

        // Mock the repository LiveData
        `when`(repository.allRounderList).thenReturn(mockLiveData)

        // Observe LiveData
        val observedData = mutableListOf<MutableList<AllRoundedResponse>?>()
        viewModel.allRounderList.observeForever { observedData.add(it) }

        // Act
        viewModel.getAllRounders()

        // Assert
        assertEquals(mockAllRounders, observedData.first())
    }

    @Test
    fun `verify getBatters updates LiveData correctly`() = runTest(testDispatcher) {
        // Arrange
        val mockData = mutableListOf(
            BatterResponse(
                userId = 1,
                userName = "Batter 1",
                userPicture = "url1",
                playerInfo = "Info 1",
                innings = 25,
                runs = 1000,
                average = 50.0,
                strikeRate = 130.0,
                highestScore = 150,
                fifties = 5,
                hundred = 2,
                worldRank = 1,
                nationalRank = 2,
                isFormar = 0
            )
        )

        val mockLiveData = MutableLiveData<MutableList<BatterResponse>>().apply {
            value = mockData
        }

        // Mock the repository LiveData
        `when`(repository.battersList).thenReturn(mockLiveData)

        // Observe LiveData
        val observedData = mutableListOf<MutableList<BatterResponse>?>()
        viewModel.battersList.observeForever { observedData.add(it) }

        // Act
        viewModel.getBatters()

        // Assert
        assertEquals(mockData, observedData.first())

    }

    @Test
    fun `verify getBowlers updates LiveData correctly`() = runTest(testDispatcher) {
        // Arrange
        val mockData = mutableListOf(
            BowlerResponse(
                userId = 1,
                userName = "Bowler 1",
                userPicture = "url1",
                playerInfo = "Info 1",
                overs = 50.0,
                wickets = 30,
                average = 18.0,
                economy = 4.2,
                best = "5/20",
                threeFa = 2,
                worldRank = 3,
                nationalRank = 5,
                isFormar = 0
            )
        )

        val mockLiveData = MutableLiveData<MutableList<BowlerResponse>>().apply {
            value = mockData
        }

        // Mock the repository LiveData
        `when`(repository.bowlerList).thenReturn(mockLiveData)

        // Observe LiveData
        val observedData = mutableListOf<MutableList<BowlerResponse>?>()
        viewModel.bowlerList.observeForever { observedData.add(it) }

        // Act
        viewModel.getBowlers()

        // Assert
        assertEquals(mockData, observedData.first())
    }

    @Test
    fun `verify getSummary updates LiveData correctly`() = runTest(testDispatcher) {
        // Arrange
        val mockData = MutableLiveData(
            TeamProfileData(
                teamInfo = listOf(
                    TeamInfo(
                        teamName = "Team A", teamLogo = "url1", sponsorLogo = "url2", teamDescription = "Team Description"
                    )
                ),
                summaryStats = listOf(
                    SummaryStats(
                        gamesPlayed = 10, winRatio = 80.0, wins = 8, loses = 2
                    )
                ),
                rankings = listOf(
                    Rankings(
                        worldRank = 1, countryRank = 1, regionalRank = 2, form = "WWWLW"
                    )
                ),
                topBatsmen = listOf(),
                topBowlers = listOf(),
                topAllRounders = listOf(),
                awards = listOf(),
                matchResults = listOf(),
                upcomingFixtures = listOf(),
                videos = listOf(),
                honors = listOf()
            )
        )
        `when`(repository.teamProfileData).thenReturn(mockData)

        // Act
        viewModel.getSummary()

        // Assert
        assertEquals(mockData.value, viewModel.teamProfileData.value)
    }
}
