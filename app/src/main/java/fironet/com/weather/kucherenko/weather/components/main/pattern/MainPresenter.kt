package fironet.com.weather.kucherenko.weather.components.main.pattern

import android.util.Log
import android.widget.Toast
import fironet.com.weather.kucherenko.weather.abstracts.presenter.BasePresenter
import fironet.com.weather.kucherenko.weather.components.utils.Const
import fironet.com.weather.kucherenko.weather.components.api.WeatherAPIService
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

class MainPresenter : BasePresenter<MainContract.View>(), MainContract.Presenter {

    val list = ArrayList<WeatherModel>()

    override fun requestWeather(cities: List<String>) {
        Flowable.fromIterable(cities)
                .flatMap {
                    requestWeather(it).onErrorResumeNext { throwable: Throwable ->
                        Flowable.empty<WeatherModel>()
                    }
                }
                .subscribe(object : DisposableSubscriber<WeatherModel>() {

                    override fun onComplete() {
                        view!!.setWeatherInfo(list)
                        list.clear()
                    }

                    override fun onNext(weatherModel: WeatherModel) {
                        list.add(weatherModel)
                        Log.e("success", weatherModel.name)
                        Toast.makeText(view!!.getContext(), weatherModel.name, Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        Log.e("error", e.message)
                        Toast.makeText(view!!.getContext(), "error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                })
    }


    override fun requestWeather(city: String): Flowable<WeatherModel> {
        return WeatherAPIService.create().search(city, Const.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

