package fironet.com.weather.kucherenko.weather.components.managers

import android.arch.persistence.room.Room
import android.util.Log
import fironet.com.weather.kucherenko.weather.components.App
import fironet.com.weather.kucherenko.weather.components.adapters.DataAdapter
import fironet.com.weather.kucherenko.weather.components.api.WeatherAPIService
import fironet.com.weather.kucherenko.weather.components.db.WeatherDatabase
import fironet.com.weather.kucherenko.weather.components.db.models.CityModel
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import fironet.com.weather.kucherenko.weather.components.utils.Const
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataReciever private constructor() {

    private val db: WeatherDatabase
    private val weatherApi: WeatherAPIService

    init {
        db = Room.databaseBuilder(App.instance!!.applicationContext, WeatherDatabase::class.java, DB_NAME).build()
        weatherApi = WeatherAPIService.create()
    }

    companion object {
        private const val DB_NAME = "search.search.android.moviedb.com.films.db"
        private var _instance: DataReciever? = null

        val instance: DataReciever
            get() {
                if (_instance == null) {
                    _instance = DataReciever()
                }
                return _instance as DataReciever
            }
    }

    fun getCitiesWeatherFromApi(cities: List<String>): Flowable<WeatherModel> {
        return Flowable.fromIterable(cities)
                .flatMap {
                    getCityWeatherFromApi(it).onErrorResumeNext { throwable: Throwable ->
                        Flowable.empty<WeatherModel>()
                    }
                }
    }

    fun getCityWeatherFromApi(city: String): Flowable<WeatherModel> {
        return weatherApi.search(city, Const.API_KEY)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCitiesCount(): Single<Int> {
        return db.weatherDao().getCitiesCount()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCitiesWeatherFromDb(): Flowable<List<CityModel>> {
//        return db.weatherDao().getCities().filter { it.isNotEmpty() }
//                .toFlowable()
//                .subscribeOn(Schedulers.single())
//                .observeOn(AndroidSchedulers.mainThread())
        return db.weatherDao().getCities()
                .toFlowable()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun storeCitiesInDb(cities: List<WeatherModel>) {
        val list = cities.map { data -> DataAdapter.castToCityModel(data) }.toList()
        Log.e("inserting", "Inserted ${list.size} items from API to DB...")
        Observable.fromCallable { db.weatherDao().insertAll(list) }
                .subscribeOn(Schedulers.single())
                .doOnNext { Log.e("inserted", "Inserted data from API to DB...") }
                .subscribe()
    }

    fun storeCityInDb(city: WeatherModel) {
        Observable.fromCallable { db.weatherDao().insert(DataAdapter.castToCityModel(city)) }
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.io())
                .doOnComplete { Log.e("inserted", "Dispatching ${city.name} from API to DB...") }
                .subscribe()
    }

}