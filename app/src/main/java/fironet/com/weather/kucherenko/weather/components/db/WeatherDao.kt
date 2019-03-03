package fironet.com.weather.kucherenko.weather.components.db

import android.arch.persistence.room.*
import io.reactivex.Single
import search.android.moviedb.com.films.db.models.WeatherDbModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM films")
    fun getItems(): Single<List<WeatherDbModel>>

    @Query("SELECT * FROM films WHERE id LIKE :id")
    fun findById(id: Int): Single<List<WeatherDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: WeatherDbModel)

    @Delete
    fun delete(entity: WeatherDbModel)
}