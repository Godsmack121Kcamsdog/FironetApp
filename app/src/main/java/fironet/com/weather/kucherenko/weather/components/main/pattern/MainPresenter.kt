package fironet.com.weather.kucherenko.weather.components.main.pattern

import android.util.Log
import android.widget.Toast
import fironet.com.weather.kucherenko.weather.abstracts.presenter.BasePresenter
import fironet.com.weather.kucherenko.weather.components.App
import fironet.com.weather.kucherenko.weather.components.adapters.DataAdapter
import fironet.com.weather.kucherenko.weather.components.managers.DataReciever
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import fironet.com.weather.kucherenko.weather.components.utils.DefaultData
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    private var list = ArrayList<WeatherModel>()
    private val manager = DataReciever.instance

    /**
     * @param cities list of cities for weather search
     * requests weather data from API or from DB/Default
     */
    override fun requestWeather(cities: List<String>) {
        if (App.instance!!.isNetworkAvailable()) {
            manager.getCitiesWeatherFromApi(cities)
                    .subscribe(object : DisposableSubscriber<WeatherModel>() {

                        override fun onComplete() {
                            view!!.setWeatherInfo(list)
                            manager.storeCitiesInDb(list)
                            list.clear()
                        }

                        override fun onNext(weatherModel: WeatherModel) {
                            list.add(weatherModel)
                            Log.e("success", weatherModel.name)
                        }

                        override fun onError(e: Throwable) {
                            Log.e("error", e.message)
                        }
                    })
        } else
            getDefaultOrFromDB()
    }

    /**
     * @param city - city name for weather search
     */
    override fun requestWeather(city: String): Flowable<WeatherModel> {

        return manager.getCityWeatherFromApi(city)
                .doOnError {
                    Toast.makeText(view!!.getContext(), "City does not exist", Toast.LENGTH_SHORT).show()
                }
                .onErrorResumeNext { throwable: Throwable ->
                    Flowable.empty<WeatherModel>()
                }.doOnNext {
                    Log.e("City", it.name)
                    if (it.name != "") {
                        view!!.setNewCity(it)
                        manager.storeCityInDb(it)
                    }
                }
    }

    /**
     * Update current stored data
     * If no data stored - save default data to DB and sets it
     */
    override fun updateWeatherInfo() {
        manager.getCitiesWeatherFromDb()
                .doOnNext {
                    if (it.isEmpty()) {
                        val list = DefaultData.getDefaultInfo()
                        manager.storeCitiesInDb(list)
                        view!!.setWeatherInfo(list)
                        this.list.addAll(list)
                        Log.e("data", "empty")
                    } else {
                        Log.e("data", "not empty")
                        val list = it.map { d -> DataAdapter.castToWeatherModel(d) }.map { d -> d.name }.toList()
                        requestWeather(list)
                    }
                }
                .subscribe()
    }

    /**
     * update current info
     */
    fun update() {
        if (list.isNotEmpty()) {
            val data = list.map { d -> d.name }.toList()
            requestWeather(data)
        } else updateWeatherInfo()
    }

    /**
     * Sets weather data from DB
     * If no data stored - save default data to DB and sets it
     */
    private fun getDefaultOrFromDB() {
        manager.getCitiesWeatherFromDb()
                .doOnNext {
                    var list = DefaultData.getDefaultInfo()
                    if (it.isEmpty()) {
                        manager.storeCitiesInDb(list)
                        Log.e("data", "empty")
                    } else {
                        Log.e("data", "not empty")
                        list = it.map { d -> DataAdapter.castToWeatherModel(d) }.toList()
                    }
                    view!!.setWeatherInfo(list)
                    this.list.clear()
                    this.list.addAll(list)
                }
                .subscribe()
    }
}

