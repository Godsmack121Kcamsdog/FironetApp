package fironet.com.weather.kucherenko.weather.abstracts

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fironet.com.weather.kucherenko.weather.abstracts.view.BaseView

abstract class BaseCompatActivity : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    override fun getContext(): Context = this
}