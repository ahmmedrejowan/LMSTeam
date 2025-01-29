package com.rejowan.lmsteamprofile.ui.shared.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments.AllRounderFragment
import com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments.BattingFragment
import com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments.BowlingFragment
import com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments.SummaryFragment

class SecondFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SummaryFragment()
            }

            1 -> {
                BattingFragment()
            }

            2 -> {
                BowlingFragment()
            }

            3 -> {
                AllRounderFragment()
            }

            else -> {
                SummaryFragment()
            }
        }

    }


}