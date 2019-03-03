package fironet.com.weather.kucherenko.weather.components.db.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * Stands for Films loaded by keyword and attached to WeatherKey column
 */
@Entity(tableName = "films")
class WeatherDbModel(val data: String,
                     @ForeignKey(entity = WeatherKey::class, parentColumns = ["id"], childColumns = ["itemId"])
           val id: Int) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var itemId: Int? = null

    override fun toString(): String {
        return "WeatherDbModel (data='$data')"
    }


}
