package fironet.com.weather.kucherenko.weather.components.db

import android.arch.persistence.room.*
import fironet.com.weather.kucherenko.weather.components.db.models.CityModel
import io.reactivex.Single

@Database(entities = [CityModel::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

@Dao
interface WeatherDao {

    @Query("SELECT * FROM cities")
    fun getCities(): Single<List<CityModel>>

    @Query("SELECT count(*) FROM cities")
    fun getCitiesCount(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: CityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<CityModel>)

    @Delete
    fun delete(entity: CityModel)
}