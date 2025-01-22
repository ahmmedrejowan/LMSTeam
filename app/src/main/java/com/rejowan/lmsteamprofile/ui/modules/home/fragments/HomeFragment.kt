package com.rejowan.lmsteamprofile.ui.modules.home.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rejowan.lmsteamprofile.data.remote.response.AllRoundedResponse
import com.rejowan.lmsteamprofile.data.remote.response.Awards
import com.rejowan.lmsteamprofile.data.remote.response.MatchResult
import com.rejowan.lmsteamprofile.data.remote.response.Player
import com.rejowan.lmsteamprofile.data.remote.response.Rankings
import com.rejowan.lmsteamprofile.data.remote.response.SummaryStats
import com.rejowan.lmsteamprofile.data.remote.response.TeamInfo
import com.rejowan.lmsteamprofile.data.remote.response.UpcomingFixture
import com.rejowan.lmsteamprofile.data.remote.response.Video
import com.rejowan.lmsteamprofile.databinding.FragmentHomeBinding
import com.rejowan.lmsteamprofile.ui.shared.adapter.RecentResultsAdapter
import com.rejowan.lmsteamprofile.ui.shared.adapter.RecentVideoAdapter
import com.rejowan.lmsteamprofile.ui.shared.adapter.SquadPlayerAdapter
import com.rejowan.lmsteamprofile.ui.shared.adapter.TopPlayerAdapter
import com.rejowan.lmsteamprofile.ui.shared.adapter.UpcomingFixturesAdapter
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.teamProfileData.observe(viewLifecycleOwner) { profileData ->
            setupTeamInfo(profileData.teamInfo)
            setupSummaryStats(profileData.summaryStats)
            setupRanking(profileData.rankings)
            setupTopBatsman(profileData.topBatsmen)
            setupTopBowler(profileData.topBowlers)
            setupTopAllRounder(profileData.topAllRounders)
            setupAwards(profileData.awards)
            setupMatchResults(profileData.matchResults)
            setupUpcomingFixtures(profileData.upcomingFixtures)
            setupVideos(profileData.videos)
        }

        mainViewModel.allRounderList.observe(viewLifecycleOwner) { allRounderList ->
            setupSquad(allRounderList)
        }

    }


    private fun setupTeamInfo(teamInfo: List<TeamInfo>) {
        binding.teamName.text = teamInfo[0].teamName

        Glide.with(requireContext()).load(teamInfo[0].teamLogo).into(binding.teamLogo)

        Glide.with(requireContext()).load(teamInfo[0].sponsorLogo).into(binding.sponsorLogo)

        binding.summaryLayout.description.text = teamInfo[0].teamDescription

    }

    private fun setupSummaryStats(summaryStats: List<SummaryStats>) {
        binding.summaryLayout.matches.text = summaryStats[0].gamesPlayed.toString()

        val winRatio = summaryStats[0].winRatio.toString()
        val winRatioArray = winRatio.split(".")
        binding.summaryLayout.winRatio.text = winRatioArray[0]
        binding.summaryLayout.winRatioPercent.text = ".${winRatioArray[1]} %"

        binding.summaryLayout.wins.text = String.format(summaryStats[0].wins.toString())
        binding.summaryLayout.loses.text = summaryStats[0].loses.toString()
    }

    private fun setupRanking(rankings: List<Rankings>) {
        binding.summaryLayout.cityRank.text = rankings[0].regionalRank.toString()
        binding.summaryLayout.nationalRank.text = rankings[0].countryRank.toString()
        binding.summaryLayout.worldRank.text = rankings[0].worldRank.toString()

        val form = rankings[0].form
        val formArray = form.split(" ")
        val spannableString = SpannableStringBuilder()
        for (formItem in formArray) {
            val start = spannableString.length
            spannableString.append(formItem)

            val color = if (formItem == "W") Color.GREEN else Color.RED

            spannableString.setSpan(
                ForegroundColorSpan(color),
                start,
                spannableString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            spannableString.append(" ")

        }
        binding.summaryLayout.form.text = spannableString


    }


    private fun setupTopBatsman(topBatsmen: List<Player>) {
        val adapter = TopPlayerAdapter(topBatsmen.take(6))
        binding.summaryLayout.recyclerViewTopBatsmen.adapter = adapter
        binding.summaryLayout.recyclerViewTopBatsmen.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupTopBowler(topBowlers: List<Player>) {
        val adapter = TopPlayerAdapter(topBowlers.take(6))
        binding.summaryLayout.recyclerViewTopBowlers.adapter = adapter
        binding.summaryLayout.recyclerViewTopBowlers.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupTopAllRounder(topAllRounders: List<Player>) {
        val adapter = TopPlayerAdapter(topAllRounders.take(6))
        binding.summaryLayout.recyclerViewTopAllRounders.adapter = adapter
        binding.summaryLayout.recyclerViewTopAllRounders.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }


    private fun setupSquad(allRounderList: MutableList<AllRoundedResponse>) {
        val adapter = SquadPlayerAdapter(allRounderList.take(3))
        binding.summaryLayout.recyclerViewSquad.adapter = adapter
        binding.summaryLayout.recyclerViewSquad.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        binding.summaryLayout.scrollSquad.setOnClickListener {
            val last = adapter.itemCount - 1
            binding.summaryLayout.recyclerViewSquad.smoothScrollToPosition(last)

        }

        binding.summaryLayout.recyclerViewSquad.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val totalItemCount = adapter.itemCount

                if (lastVisiblePosition == totalItemCount - 1) {
                    binding.summaryLayout.scrollSquad.visibility = View.GONE
                } else {
                    binding.summaryLayout.scrollSquad.visibility = View.VISIBLE
                }
            }
        })

    }

    private fun setupAwards(awards: List<Awards>) {
        binding.summaryLayout.champions.text = awards[0].champion.toString()
        binding.summaryLayout.runersUp.text = awards[0].runnersUp.toString()
    }

    private fun setupMatchResults(matchResults: List<MatchResult>) {
        val adapter = RecentResultsAdapter(matchResults.take(5))
        binding.summaryLayout.recyclerViewRecentResults.adapter = adapter
        binding.summaryLayout.recyclerViewRecentResults.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

    }

    private fun setupUpcomingFixtures(upcomingFixtures: List<UpcomingFixture>) {
        val adapter = UpcomingFixturesAdapter(upcomingFixtures.take(5))
        binding.summaryLayout.recyclerViewUpcomingFixtures.adapter = adapter
        binding.summaryLayout.recyclerViewUpcomingFixtures.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setupVideos(videos: List<Video>) {
        val adapter = RecentVideoAdapter(videos.take(3))
        binding.summaryLayout.recyclerViewVideos.adapter = adapter
        binding.summaryLayout.recyclerViewVideos.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

    }


}