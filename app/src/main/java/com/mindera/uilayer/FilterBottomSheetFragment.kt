package com.mindera.uilayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mindera.rocketscience.R
import com.mindera.rocketscience.utility.LaunchesFilter
import kotlinx.android.synthetic.main.spacxdata_filter_bottomsheet.*


class FilterBottomSheetFragment(val activity: MainActivity) : BottomSheetDialogFragment(),
    View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.spacxdata_filter_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvFilterSuccess.setOnClickListener(this)
        tvFilterFail.setOnClickListener(this)
        tvFilterAsc.setOnClickListener(this)
        tvFilterDesc.setOnClickListener(this)
        tvFilterClear.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvFilterSuccess -> getFilteredData(LaunchesFilter.SUCCESSFUL)
            R.id.tvFilterFail -> getFilteredData(LaunchesFilter.FAIL)
            R.id.tvFilterAsc -> getFilteredData(LaunchesFilter.ASC)
            R.id.tvFilterDesc -> getFilteredData(LaunchesFilter.DESC)
            R.id.tvFilterClear -> getFilteredData(LaunchesFilter.CLEAR)

        }
    }

    private fun getFilteredData(launchesFilter: LaunchesFilter) {
        (activity as MainActivity).filterLaunchesList(launchesFilter)
        this.dismiss()
    }

}