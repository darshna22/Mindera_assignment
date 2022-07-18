package com.mindera.rocketscience.utility

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri


object OpenLinksPage {

    fun openWikiPedia(url: String, activity: Activity) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }

    fun openYoutubeLink(youtubeID: String, activity: Activity) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            activity.startActivity(intentBrowser)
        }

    }
}