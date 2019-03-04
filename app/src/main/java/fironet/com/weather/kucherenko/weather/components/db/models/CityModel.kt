package fironet.com.weather.kucherenko.weather.components.db.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Stands for Films loaded by keyword and attached to WeatherKey column
 */
@Entity(tableName = "cities")
class CityModel(@PrimaryKey
                     @ColumnInfo(name = "city")
                     val city: String,
                @ColumnInfo(name = "data")
                     val data: String)
