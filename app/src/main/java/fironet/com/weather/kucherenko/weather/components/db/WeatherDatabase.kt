package fironet.com.weather.kucherenko.weather.components.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import search.android.moviedb.com.films.db.models.WeatherDbModel
import search.android.moviedb.com.films.db.models.WeatherKey

@Database(entities = [WeatherKey::class, WeatherDbModel::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun keysDao(): CitiesDao
    abstract fun filmsDao(): WeatherDao
}