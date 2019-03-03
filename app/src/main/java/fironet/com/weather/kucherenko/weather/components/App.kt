package fironet.com.weather.kucherenko.weather.components

import android.app.Application
import android.content.res.Resources

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

    companion object {
        var instance: App? = null
            private set
    }
}