package com.rejowan.lmsteamprofile.ui.shared.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rejowan.lmsteamprofile.ui.modules.home.fragments.HomeFragment
import com.rejowan.lmsteamprofile.ui.modules.home.fragments.MoreFragment
import com.rejowan.lmsteamprofile.ui.modules.home.fragments.MyLmsFragment
import com.rejowan.lmsteamprofile.ui.modules.home.fragments.ProfileFragment
import com.rejowan.lmsteamprofile.ui.modules.home.fragments.ShopFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }

            1 -> {
                ProfileFragment()
            }

            2 -> {
                MyLmsFragment()
            }

            3 -> {
                ShopFragment()
            }

            4 -> {
                MoreFragment()
            }

            else -> {
                HomeFragment()
            }
        }

    }


}