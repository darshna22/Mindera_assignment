package com.mindera.uilayer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindera.rocketscience.MyApplication
import com.mindera.rocketscience.R
import com.mindera.rocketscience.dagger.activity.ActivityModule
import com.mindera.rocketscience.dagger.activity.DaggerActivityComponent
import com.mindera.rocketscience.databinding.ActivityMainBinding
import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.utility.CheckValidation
import com.mindera.rocketscience.utility.Constants.pageLimit
import com.mindera.rocketscience.utility.LaunchesFilter
import com.mindera.rocketscience.utility.PaginationScrollListener
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleOwner {
    lateinit var binding: ActivityMainBinding
    lateinit var spaceXDataAdapter: SpaceXDataAdapter
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 1
    private var currentPage: Int = pageStart
    private var allLaunchesItemList: MutableList<AllLaunchesRespItem> = ArrayList()
    var filterList: MutableList<AllLaunchesRespItem> = ArrayList()

    @Inject
    lateinit var spaceXDataViewModel: SpaceXDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val activityComponent = DaggerActivityComponent.builder()
            .myAppComponent((application as MyApplication).appComponent)
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)

        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewModel = spaceXDataViewModel
        initViews()
        observerDataRequest()

    }

    private fun initViews() {
        spaceXDataAdapter = SpaceXDataAdapter(this@MainActivity)
        binding.adapterSpaceXData = spaceXDataAdapter
        binding.recyclerView.apply {
            this.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = spaceXDataAdapter
            itemAnimator = DefaultItemAnimator()
        }
        //all api's including pager of all launches
        loadFirstPage()
        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(binding.recyclerView.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1

                Handler(Looper.myLooper()!!).postDelayed({
                    loadNextPage()
                }, 1000)
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

    }

    private fun observerDataRequest() {
        spaceXDataViewModel.error.observe(this) {
            errorAlert(it)
        }
        spaceXDataViewModel.allLaunchesRespItemFirstPage.observe(this) {
            binding.mainProgress.visibility = View.GONE
            addAllLaunchesPageData(it as MutableList<AllLaunchesRespItem>)
        }

        spaceXDataViewModel.allLaunchesRespItemNextPage.observe(this) {
            spaceXDataAdapter.removeLoadingFooter()
            isLoading = false

            addAllLaunchesPageData(it as MutableList<AllLaunchesRespItem>)

        }
    }

    private fun addAllLaunchesPageData(results: MutableList<AllLaunchesRespItem>) {
        allLaunchesItemList.addAll(results)
        spaceXDataAdapter.addAll(allLaunchesItemList)
        if (results.size in 1..pageLimit)
            totalPages++
        else
            isLastPage = true

        if (currentPage != totalPages) {
            spaceXDataAdapter.addLoadingFooter()
            isLastPage = false
        }
    }

    private fun loadFirstPage() {
        if (CheckValidation.isConnected(this)) {
            spaceXDataViewModel.callAPI()
        } else {
            showInternetError()
        }
    }

    private fun showInternetError() {
        spaceXDataViewModel.error.value = resources.getString(R.string.error_msg_no_internet)
    }

    fun loadNextPage() {
        if (CheckValidation.isConnected(this)) {
            val offset = (currentPage - 1) * pageLimit
            spaceXDataViewModel.callNextPageAllLaunchesApi(pageLimit, offset)
        } else {
            showInternetError()
            spaceXDataAdapter.showRetry(true, resources.getString(R.string.error_msg_no_internet))
        }
    }

    private fun errorAlert(errorCause: String) {

        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle(resources.getString(R.string.error_msg))
            setMessage(errorCause)
            setPositiveButton(
                getString(R.string.okay_button)
            ) { dialog, which ->
                dialog.dismiss()
            }
            show()
        }


    }

    fun filterClick(view: View) {
        FilterBottomSheetFragment(this).show(supportFragmentManager, "dialog");
    }


    fun filterLaunchesList(filterType: LaunchesFilter) {
        clearPreviousFilterList()
        when (filterType) {
            LaunchesFilter.SUCCESSFUL -> {
                filterList(true)
            }
            LaunchesFilter.FAIL -> {
                filterList(false)
            }
            LaunchesFilter.ASC -> {
                filterList.sortBy { it.mission_name }
            }
            LaunchesFilter.DESC -> {
                filterList.sortByDescending { it.mission_name }

            }
            LaunchesFilter.CLEAR -> {
            }
        }
        spaceXDataAdapter.filterListData(filterList)
    }

    private fun clearPreviousFilterList() {
        filterList.clear()
        filterList.addAll(allLaunchesItemList)

    }

    private fun filterList(boolean: Boolean) {
        val list = filterList
        val data = filterList.filter { it.launch_success == boolean }
        filterList.clear()
        filterList.addAll(data)
    }


}