package com.mindera.rocketscience.utility

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission

class CheckValidation {

    companion object{

        /**
         * Get the network info
         *
         * @param context the context
         * @return network info
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        private fun getNetworkInfo(context: Context): NetworkInfo? {
            val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            return cm.activeNetworkInfo
        }

        /**
         * Check if there is any connectivity
         *
         * @param context the context
         * @return boolean boolean
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnected(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected
        }
    }
}