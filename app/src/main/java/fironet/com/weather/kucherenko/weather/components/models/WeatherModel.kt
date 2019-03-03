package fironet.com.weather.kucherenko.weather.components.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WeatherModel(

        @SerializedName("coord")
        @Expose
        var coord: Coord? = null,
        @SerializedName("weather")
        @Expose
        var weather: List<Weather>? = null,
        @SerializedName("base")
        @Expose
        var base: String? = null,
        @SerializedName("main")
        @Expose
        var main: Main? = null,
        @SerializedName("visibility")
        @Expose
        var visibility: Int? = null,
        @SerializedName("wind")
        @Expose
        var wind: Wind? = null,
        @SerializedName("clouds")
        @Expose
        var clouds: Clouds?,
        @SerializedName("dt")
        @Expose
        var dt: Int? = null,
        @SerializedName("sys")
        @Expose
        var sys: Sys? = null,
        @SerializedName("id")
        @Expose
        var id: Int? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("cod")
        @Expose
        var cod: Int? = null

) {
    override fun toString(): String {
        var data = ""

        data = data.plus("Name:").plus("$name \n")
        data = data.plus("Coord:").plus("lat: ${coord!!.lat} lon: ${coord!!.lon} \n")
        data = data.plus("Weather: ${weather?.get(0)!!.description} \n")
        data = data.plus("Base: $base \n")
        data = data.plus("Main: \n")
        data = data.plus("  temp_min: ${main!!.tempMin} \n")
        data = data.plus("  temp: ${main!!.temp} \n")
        data = data.plus("  temp_max: ${main!!.tempMax} \n")
        data = data.plus("visibility: $visibility \n")
        data = data.plus("Wind: \n")
        data = data.plus("  speed: ${wind!!.speed}: \n")
        data = data.plus("  deg: ${wind!!.deg}: \n")
        data = data.plus("Clouds: ${clouds!!.all} \n")
        data = data.plus("Dt: $dt \n")
        data = data.plus("Sys: \n")
        data = data.plus("  type: ${sys!!.type} \n")
        data = data.plus("  message: ${sys!!.message} \n")
        data = data.plus("  id: ${sys!!.id} \n")
        data = data.plus("Id: $id \n")
        data = data.plus("Cod: $cod")

        return data
    }
}
