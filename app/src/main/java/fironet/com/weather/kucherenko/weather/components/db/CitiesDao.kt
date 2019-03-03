package fironet.com.weather.kucherenko.weather.components.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import fironet.com.weather.kucherenko.weather.components.db.models.WeatherKey

import io.reactivex.Single

@Dao
interface CitiesDao {

    @Query("SELECT * FROM keys")
    fun getAll(): Single<List<WeatherKey>>

    @Query("SELECT * FROM keys WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): Single<WeatherKey>

    @Insert
    fun insertAll(vararg entities: WeatherKey)

    @Insert(onConflict = REPLACE)
    fun insert(entity: WeatherKey)

    @Delete
    fun delete(entity: WeatherKey)
}
