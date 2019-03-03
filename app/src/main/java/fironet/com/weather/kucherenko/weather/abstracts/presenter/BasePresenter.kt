package fironet.com.weather.kucherenko.weather.abstracts.presenter

import android.util.Log
import fironet.com.weather.kucherenko.weather.abstracts.view.BaseView

open class BasePresenter<V : BaseView> : BaseMvpPresenter<V> {
    protected var view: V? = null
        private set

    override var isAttached = view != null

    override fun attach(view: V) {
        this.view = view
    }

    override fun detach() {
        this.view = null
    }

    fun onError(error: Throwable) {
        Log.e("error", error.message)
    }
}