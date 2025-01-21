package com.rejowan.lmsteamprofile.ui.modules.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rejowan.lmsteamprofile.data.remote.response.TeamInfo
import com.rejowan.lmsteamprofile.databinding.FragmentHomeBinding
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mainViewModel.teamProfileData.observe(viewLifecycleOwner) { profileData ->
            setupTeamInfo(profileData.teamInfo)
        }

        setupBasicUI()


    }

    private fun setupTeamInfo(teamInfo: List<TeamInfo>) {
        binding.teamName.text = teamInfo[0].teamName

        Glide.with(requireContext())
            .load(teamInfo[0].teamLogo)
            .into(binding.teamLogo)

        Glide.with(requireContext())
            .load(teamInfo[0].sponsorLogo)
            .into(binding.sponsorLogo)


    }

    private fun setupBasicUI() {


    }


}