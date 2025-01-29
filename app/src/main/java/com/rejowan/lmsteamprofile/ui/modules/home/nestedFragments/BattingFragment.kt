package com.rejowan.lmsteamprofile.ui.modules.home.nestedFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rejowan.lmsteamprofile.R
import com.rejowan.lmsteamprofile.data.remote.response.BatterResponse
import com.rejowan.lmsteamprofile.databinding.FragmentBattingBinding
import com.rejowan.lmsteamprofile.ui.shared.adapter.BatsmanAdapter
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BattingFragment : Fragment() {

    private val binding: FragmentBattingBinding by lazy {
        FragmentBattingBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    private val adapter: BatsmanAdapter by lazy {
        BatsmanAdapter(mutableListOf())
    }

    private val batterList = mutableListOf<BatterResponse>()
    private val batterWithoutFormerList = mutableListOf<BatterResponse>()

    private var showFormer = false
    private var isRunsSortedDesc = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupAdapter()

        setupFilters()

        setupClicks()

    }

    private fun setupClicks() {

        binding.loadMore.setOnClickListener {
            mainViewModel.loadMoreBatters()
        }

    }

    private fun setupFilters() {

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                adapter.updateList(batterList)
            } else {
                adapter.updateList(batterWithoutFormerList)
            }
            showFormer = isChecked

        }

        binding.runsSort.setOnClickListener {

            val sortedList = if (showFormer) {
                if (isRunsSortedDesc) batterList.sortedByDescending { it.runs }
                else batterList.sortedBy { it.runs }
            } else {
                if (isRunsSortedDesc) batterWithoutFormerList.sortedByDescending { it.runs }
                else batterWithoutFormerList.sortedBy { it.runs }
            }

            adapter.updateList(sortedList)

            val sortIconRes = if (isRunsSortedDesc) R.drawable.ic_triangle_down else R.drawable.ic_triangle_up
            binding.runsSort.setImageResource(sortIconRes)
            isRunsSortedDesc = !isRunsSortedDesc

        }


    }

    private fun setupAdapter() {

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        binding.recyclerView.setHasFixedSize(true)


        mainViewModel.battersList.observe(viewLifecycleOwner) { list ->

            CoroutineScope(Dispatchers.IO).launch {
                batterList.clear()
                batterWithoutFormerList.clear()
                batterList.addAll(list)

                batterWithoutFormerList.addAll(list.filter { it.isFormar == 0 })

                lifecycleScope.launch {
                    adapter.initList(batterWithoutFormerList)
                }

            }
        }


    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}