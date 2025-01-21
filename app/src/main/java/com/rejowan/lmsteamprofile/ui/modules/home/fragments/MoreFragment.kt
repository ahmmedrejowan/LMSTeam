package com.rejowan.lmsteamprofile.ui.modules.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rejowan.lmsteamprofile.databinding.FragmentOthersBinding

class MoreFragment : Fragment() {

    private val binding: FragmentOthersBinding by lazy {
        FragmentOthersBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentName.text = "More Fragment"
    }


}