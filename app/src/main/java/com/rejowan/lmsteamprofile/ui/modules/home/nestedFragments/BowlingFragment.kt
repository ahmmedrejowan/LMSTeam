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
import com.rejowan.lmsteamprofile.data.remote.response.BowlerResponse
import com.rejowan.lmsteamprofile.databinding.FragmentBowlingBinding
import com.rejowan.lmsteamprofile.ui.shared.adapter.BowlerAdapter
import com.rejowan.lmsteamprofile.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BowlingFragment : Fragment() {

    private val binding: FragmentBowlingBinding by lazy {
        FragmentBowlingBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModel()

    private val adapter: BowlerAdapter by lazy {
        BowlerAdapter(mutableListOf())
    }

    private val batterList = mutableListOf<BowlerResponse>()
    private val batterWithoutFormerList = mutableListOf<BowlerResponse>()

    private var showFormer = false
    private var isWicketSortedDesc = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(activity, "Loading Data", Toast.LENGTH_SHORT).show()

        setupAdapter()

        setupFilters()

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

        binding.wktsSort.setOnClickListener {

            val sortedList = if (showFormer) {
                if (isWicketSortedDesc) batterList.sortedByDescending { it.wickets }
                else batterList.sortedBy { it.wickets }
            } else {
                if (isWicketSortedDesc) batterWithoutFormerList.sortedByDescending { it.wickets }
                else batterWithoutFormerList.sortedBy { it.wickets }
            }

            adapter.updateList(sortedList)

            val sortIconRes = if (isWicketSortedDesc) R.drawable.ic_triangle_down else R.drawable.ic_triangle_up
            binding.wktsSort.setImageResource(sortIconRes)
            isWicketSortedDesc = !isWicketSortedDesc

        }


    }

    private fun setupAdapter() {

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        binding.recyclerView.setHasFixedSize(true)


        mainViewModel.bowlerList.observe(viewLifecycleOwner) { list ->

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