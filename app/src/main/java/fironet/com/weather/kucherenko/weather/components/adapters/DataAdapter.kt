package fironet.com.weather.kucherenko.weather.components.adapters

import com.google.gson.Gson
import fironet.com.weather.kucherenko.weather.components.db.models.CityModel
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel

object DataAdapter {

    fun castToCityModel(model: WeatherModel): CityModel {
        val data = Gson().toJson(model)
        return CityModel(model.name, data)
    }

    fun castToWeatherModel(model: CityModel): WeatherModel {
        return Gson().fromJson(model.data, WeatherModel::class.java)
    }
}