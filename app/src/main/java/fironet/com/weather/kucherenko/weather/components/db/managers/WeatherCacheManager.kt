package fironet.com.weather.kucherenko.weather.components.db.managers

import android.arch.persistence.room.Room
import android.content.Context
import fironet.com.weather.kucherenko.weather.components.App
import fironet.com.weather.kucherenko.weather.components.db.WeatherDatabase
import fironet.com.weather.kucherenko.weather.components.db.models.WeatherDbModel
import fironet.com.weather.kucherenko.weather.components.db.models.WeatherKey
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Manager for working with database
 */
class WeatherCacheManager private constructor(context: Context) {

    private val db: WeatherDatabase

    init {
        db = Room.databaseBuilder(context, WeatherDatabase::class.java, DB_NAME).build()
    }

    companion object {
        private const val DB_NAME = "fironet.android.com.weather.db"
        private var _instance: WeatherCacheManager? = null

        val instance: WeatherCacheManager
            get() {
                if (_instance == null) {
                    _instance = WeatherCacheManager(App.instance!!.applicationContext)
                }
                return _instance as WeatherCacheManager
            }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     SEARCH PART                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * is used for getting keyword of films to load
     */
    fun findByName(name: String): Single<WeatherKey> {
        return db.keysDao().findByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * @return Single class with list of Films attached to keyword, founded by id
     */
    fun findFilms(id: Int): Single<List<WeatherDbModel>> {
        return db.filmsDao().findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }


////////////////////////////////////////////////////////////////////////////////////////////////////
//                                     INSERT PART                                                //
////////////////////////////////////////////////////////////////////////////////////////////////////

    fun insert(entity: WeatherKey) {
        Completable.fromAction {
            db.keysDao().insert(entity)
            println(entity.name + " was saved to search.android.moviedb.com.films.db")
        }.subscribeOn(Schedulers.io())
                .doOnError(::onError)
                .subscribe()
    }

    fun insert(item: WeatherDbModel) {
        Completable.fromAction { db.filmsDao().insert(item) }.subscribe()
    }

    fun insertFilms(items: List<WeatherDbModel>) {
        Completable.fromAction {
            for (i in items) {
                db.filmsDao().insert(i)
            }
        }.subscribeOn(Schedulers.io())
                .doOnError(::onError)
                .subscribe()
    }

    fun insertAll(vararg entities: WeatherKey) {

    }

    private fun onError(e: Throwable) {
        System.err.println(e.message + "\n" + e.cause.toString())
    }

}
