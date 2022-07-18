package com.mindera.rocketscience.model

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mindera.rocketscience.R
import java.io.Serializable

class AllLaunchesRespItem() : Serializable {
    val flight_number: Int? = null
    val is_tentative: Boolean? = null
    val launch_date_local: String? = null
    val launch_date_unix: Long? = null
    val launch_date_utc: String? = null
    val launch_success: Boolean? = null
    val launch_window: Int? = null
    val launch_year: String? = null
    val links: Links? = null
    val mission_name: String? = null
    val rocket: Rocket? = null
    val tbd: Boolean? = null
    val tentative_max_precision: String? = null
    val upcoming: Boolean? = null


   companion object{
       @JvmStatic
       @BindingAdapter("app:imageSpaceXDataLaunch", "app:progressSpaceXLaunch","app:placeHolder")
       fun setImageSpaceXLaunch(image: ImageView, imageUrl: String?, progressSpaceXLaunch: ProgressBar,placeHolder:Int) {
           val url = imageUrl
           Glide.with(image.context)
               .load(url)
               .apply( RequestOptions().placeholder(placeHolder).error(placeHolder))
               .listener(object : RequestListener<Drawable> {
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressSpaceXLaunch.visibility = View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       progressSpaceXLaunch.visibility = View.GONE
                       return false
                   }

               })
               .diskCacheStrategy(DiskCacheStrategy.ALL)
               .centerCrop()
               .into(image)

       }

   }
}


class Links : Serializable {
    val article_link: String? = null
    val mission_patch: String? = null
    val mission_patch_small: String? = null
    val video_link: String? = null
    val wikipedia: String? = null
    val youtube_id: String? = null
}

class Rocket() : Serializable {
    val rocket_id: String? = null
    val rocket_name: String? = null
    val rocket_type: String? = null
}

