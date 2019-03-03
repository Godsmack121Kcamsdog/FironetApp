package fironet.com.weather.kucherenko.weather.components.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Stands for entered keyword
 */
@Entity(tableName = "keys")
class WeatherKey(@PrimaryKey val id: Int, val name: String)