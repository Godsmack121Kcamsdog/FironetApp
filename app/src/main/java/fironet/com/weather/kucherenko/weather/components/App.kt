package fironet.com.weather.kucherenko.weather.components

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager


class App : Application() {

    /**
     * Simplifying of getting application resources
     *
     * @return resources
     */
    var mResources: Resources? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        mResources = resources
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    companion object {
        var instance: App? = null
            private set
    }
}