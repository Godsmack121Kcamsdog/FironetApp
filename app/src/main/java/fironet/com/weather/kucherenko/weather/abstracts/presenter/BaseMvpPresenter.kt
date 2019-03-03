package fironet.com.weather.kucherenko.weather.abstracts.presenter

import fironet.com.weather.kucherenko.weather.abstracts.view.BaseView

interface BaseMvpPresenter<V : BaseView> {

    var isAttached: Boolean
    fun attach(view: V)
    fun detach()
}