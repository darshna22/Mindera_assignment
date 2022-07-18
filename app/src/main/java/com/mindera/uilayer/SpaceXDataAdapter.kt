package com.mindera.uilayer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mindera.rocketscience.BR
import com.mindera.uilayer.interfaceies.PaginationAdapterCallback
import com.mindera.rocketscience.R
import com.mindera.rocketscience.databinding.ItemLoadingBinding
import com.mindera.rocketscience.databinding.LaunchesRawBinding
import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.utility.LaunchesFilter
import com.mindera.rocketscience.utility.OpenLinksPage
import com.mindera.rocketscience.utility.UTCTimeConvertor

class SpaceXDataAdapter constructor(private var mActivity: MainActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    PaginationAdapterCallback {

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = ""

    private var allLaunchesItemList: MutableList<AllLaunchesRespItem> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == item) {
            val binding: LaunchesRawBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.launches_raw,
                parent,
                false
            )
            TopMoviesVH(binding)
        } else {
            val binding: ItemLoadingBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_loading,
                parent,
                false
            )
            LoadingVH(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = allLaunchesItemList[position]
        if (getItemViewType(position) == item) {
            val myOrderVH: TopMoviesVH = holder as TopMoviesVH
            myOrderVH.itemRowBinding.spaceXDataProgress.visibility = View.VISIBLE
            myOrderVH.bind(model, mActivity)
        } else {
            val loadingVH: LoadingVH = holder as LoadingVH
            if (retryPageLoad) {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.VISIBLE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.GONE

                if (errorMsg != null) loadingVH.itemRowBinding.loadmoreErrortxt.text = errorMsg
                else loadingVH.itemRowBinding.loadmoreErrortxt.text =
                    mActivity.getString(R.string.error_msg_unknown)

            } else {
                loadingVH.itemRowBinding.loadmoreErrorlayout.visibility = View.GONE
                loadingVH.itemRowBinding.loadmoreProgress.visibility = View.VISIBLE
            }

            loadingVH.itemRowBinding.loadmoreRetry.setOnClickListener {
                showRetry(false, "")
                retryPageLoad()
            }
            loadingVH.itemRowBinding.loadmoreErrorlayout.setOnClickListener {
                showRetry(false, "")
                retryPageLoad()
            }
        }
    }

    override fun getItemCount(): Int {
        return if (allLaunchesItemList.size > 0) allLaunchesItemList.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            item
        } else {
            if (position == allLaunchesItemList.size - 1 && isLoadingAdded) {
                loading
            } else {
                item
            }
        }
    }

    override fun retryPageLoad() {
        mActivity.loadNextPage()
    }


    class TopMoviesVH(binding: LaunchesRawBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: LaunchesRawBinding = binding
        fun bind(allLaunchesRespItem: AllLaunchesRespItem?, activity: MainActivity) {
            itemRowBinding.setVariable(BR.model, allLaunchesRespItem)
            itemRowBinding.setVariable(BR.dateTimeValue, getFormattedDate(allLaunchesRespItem))
            itemRowBinding.setVariable(BR.day,
                allLaunchesRespItem?.launch_date_unix?.let { UTCTimeConvertor.getRemainingDays(it) })
            itemRowBinding.executePendingBindings()
            itemRowBinding.relativeLayout.setOnClickListener {
                allLaunchesRespItem?.let { it1 -> LinksDetailsBottomSheetFragment(activity, it1).show(activity.supportFragmentManager, "dialog") };


            }
        }

        private fun getFormattedDate(allLaunchesRespItem: AllLaunchesRespItem?): String {
            val mFormattedDate =
                UTCTimeConvertor.getDateAndTime(allLaunchesRespItem?.launch_date_utc)
            val utcDate = mFormattedDate.split(" ")
            return ((utcDate[0]) + " at " + (utcDate[1] + utcDate[2]))
        }
    }

    class LoadingVH(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        var itemRowBinding: ItemLoadingBinding = binding
    }

    fun showRetry(show: Boolean, errorMsg: String) {
        retryPageLoad = show
        notifyItemChanged(allLaunchesItemList.size - 1)
        this.errorMsg = errorMsg
    }

    fun addAll(allLaunchesRespItem: MutableList<AllLaunchesRespItem>) {
        allLaunchesItemList.addAll(allLaunchesRespItem)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        allLaunchesItemList.add(AllLaunchesRespItem())
        notifyItemInserted(allLaunchesItemList.size - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = allLaunchesItemList.size - 1
        allLaunchesItemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun filterListData(list: MutableList<AllLaunchesRespItem>) {
        allLaunchesItemList.clear()
        allLaunchesItemList.addAll(list)
        notifyDataSetChanged()
    }
}