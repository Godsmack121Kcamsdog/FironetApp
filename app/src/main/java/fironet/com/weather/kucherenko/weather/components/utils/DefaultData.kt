package fironet.com.weather.kucherenko.weather.components.utils

import com.google.gson.Gson
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import java.util.*

object DefaultData {

    fun getDefaultInfo(): List<WeatherModel> {
        val data = LinkedList<String>()
        data.add("{\"base\":\"stations\",\"clouds\":{\"all\":0},\"cod\":200,\"coord\":{\"lat\":52.52,\"lon\":13.39},\"dt\":1551661029,\"id\":2950159,\"main\":{\"humidity\":76,\"pressure\":999,\"temp\":282.96,\"temp_max\":284.26,\"temp_min\":282.04},\"name\":\"Berlin\",\"sys\":{\"country\":\"DE\",\"id\":1275,\"message\":0.0056,\"sunrise\":1551678420,\"sunset\":1551718219,\"type\":1},\"visibility\":10000,\"weather\":[{\"description\":\"clear sky\",\"icon\":\"01n\",\"id\":800,\"main\":\"Clear\"}],\"wind\":{\"deg\":220,\"speed\":7.2}}")
        data.add("{\"base\":\"stations\",\"clouds\":{\"all\":88},\"cod\":200,\"coord\":{\"lat\":51.51,\"lon\":-0.13},\"dt\":1551660906,\"id\":2643743,\"main\":{\"humidity\":76,\"pressure\":988,\"temp\":281.18,\"temp_max\":282.59,\"temp_min\":279.82},\"name\":\"London\",\"sys\":{\"country\":\"GB\",\"id\":1417,\"message\":0.0075,\"sunrise\":1551681594,\"sunset\":1551721532,\"type\":1},\"visibility\":10000,\"weather\":[{\"description\":\"moderate rain\",\"icon\":\"10n\",\"id\":501,\"main\":\"Rain\"}],\"wind\":{\"deg\":230,\"speed\":5.1}}")
        data.add("{\"base\":\"stations\",\"clouds\":{\"all\":90},\"cod\":200,\"coord\":{\"lat\":50.43,\"lon\":30.52},\"dt\":1551660851,\"id\":703448,\"main\":{\"humidity\":64,\"pressure\":1004,\"temp\":276.4,\"temp_max\":277.04,\"temp_min\":276.15},\"name\":\"Kiev\",\"sys\":{\"country\":\"UA\",\"id\":8903,\"message\":0.0039,\"sunrise\":1551674181,\"sunset\":1551714233,\"type\":1},\"visibility\":10000,\"weather\":[{\"description\":\"light rain\",\"icon\":\"10n\",\"id\":500,\"main\":\"Rain\"}],\"wind\":{\"deg\":240,\"speed\":6.0}}")
        data.add("{\"base\":\"stations\",\"clouds\":{\"all\":80},\"cod\":200,\"coord\":{\"lat\":48.86,\"lon\":2.35},\"dt\":1551661111,\"id\":2988507,\"main\":{\"humidity\":66,\"pressure\":999,\"temp\":283.85,\"temp_max\":285.15,\"temp_min\":282.59},\"name\":\"Paris\",\"sys\":{\"country\":\"FR\",\"id\":6540,\"message\":0.0057,\"sunrise\":1551680838,\"sunset\":1551721093,\"type\":1},\"visibility\":10000,\"weather\":[{\"description\":\"moderate rain\",\"icon\":\"10n\",\"id\":501,\"main\":\"Rain\"}],\"wind\":{\"deg\":190,\"speed\":10.8}}")
        val gson = Gson()
        return data.map { d -> gson.fromJson(d, WeatherModel::class.java) }.toList()
    }

}