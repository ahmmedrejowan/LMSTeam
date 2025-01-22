package com.rejowan.lmsteamprofile.ui.modules.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.rejowan.lmsteamprofile.data.remote.response.TeamInfo
import com.rejowan.lmsteamprofile.databinding.FragmentHomeBinding
import com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments.SummaryFragment
import com.rejowan.lmsteamprofile.ui.shared.adapter.SecondFragmentAdapter
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), SummaryFragment.PageSwitcher {

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

        }

        setupViewPager()

    }

    private fun setupViewPager() {
        val fragmentAdapter =
            SecondFragmentAdapter(childFragmentManager, lifecycle)
        binding.viewPager2.adapter = fragmentAdapter
        binding.viewPager2.isUserInputEnabled = false

        val summaryTab = binding.tabLayout.getTabAt(0)
        val battingTab = binding.tabLayout.getTabAt(1)
        val ballingTab = binding.tabLayout.getTabAt(2)
        val allRounderTab = binding.tabLayout.getTabAt(3)
        summaryTab?.select()
        binding.viewPager2.currentItem = 0

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab) {
                    summaryTab -> {
                        binding.viewPager2.setCurrentItem(0, false)
                    }

                    battingTab -> {
                        binding.viewPager2.setCurrentItem(1, false)
                    }

                    ballingTab -> {
                        binding.viewPager2.setCurrentItem(2, false)
                    }

                    allRounderTab -> {
                        binding.viewPager2.setCurrentItem(3, false)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }


    private fun setupTeamInfo(teamInfo: List<TeamInfo>) {
        binding.teamName.text = teamInfo[0].teamName

        Glide.with(requireContext()).load(teamInfo[0].teamLogo).into(binding.teamLogo)

        Glide.with(requireContext()).load(teamInfo[0].sponsorLogo).into(binding.sponsorLogo)


    }

    override fun switchPageInParent(pageIndex: Int) {
        binding.viewPager2.setCurrentItem(pageIndex, false)
        binding.tabLayout.getTabAt(pageIndex)?.select()
    }


}