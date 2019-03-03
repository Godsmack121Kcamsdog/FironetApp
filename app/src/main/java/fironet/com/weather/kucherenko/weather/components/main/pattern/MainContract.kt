package fironet.com.weather.kucherenko.weather.components.main.pattern

import fironet.com.weather.kucherenko.weather.abstracts.presenter.BaseMvpPresenter
import fironet.com.weather.kucherenko.weather.abstracts.view.BaseView
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import io.reactivex.Flowable

interface MainContract {
    interface Presenter : BaseMvpPresenter<MainContract.View> {
        fun requestWeather(city: String): Flowable<WeatherModel>
        fun requestWeather(cities: List<String>)
    }

    interface View : BaseView {
        fun addNewCity(name: String)
        fun openDetails(city:WeatherModel?)
        fun setWeatherInfo(list: List<WeatherModel>)
    }
}