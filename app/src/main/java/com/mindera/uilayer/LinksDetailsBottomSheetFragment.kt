package com.mindera.uilayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mindera.rocketscience.R
import com.mindera.rocketscience.model.AllLaunchesRespItem
import com.mindera.rocketscience.utility.OpenLinksPage
import kotlinx.android.synthetic.main.open_links_bottomsheet.*


class LinksDetailsBottomSheetFragment(val activity: MainActivity, val allLaunchesRespItem: AllLaunchesRespItem) :
    BottomSheetDialogFragment(),
    View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.open_links_bottomsheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvWikipedia.setOnClickListener(this)
        tvYouTube.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvWikipedia -> {
                allLaunchesRespItem.links?.wikipedia?.let { OpenLinksPage.openWikiPedia(it, activity) }
                this.dismiss()
            }
            R.id.tvYouTube -> {
                allLaunchesRespItem.links?.youtube_id?.let { OpenLinksPage.openYoutubeLink(it, activity) }
                this.dismiss()

            }

        }
    }


}