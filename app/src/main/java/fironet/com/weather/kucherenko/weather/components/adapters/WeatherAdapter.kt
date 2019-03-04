package fironet.com.weather.kucherenko.weather.components.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fironet.com.weather.kucherenko.weather.R
import fironet.com.weather.kucherenko.weather.abstracts.view.BaseView
import fironet.com.weather.kucherenko.weather.components.main.ui.MainActivity
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel

class WeatherAdapter(private var view: BaseView) : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(view.getContext())
    private val cities: MutableList<WeatherModel>
    private val citiesMap: HashMap<String, Int>

    init {
        cities = ArrayList()
        citiesMap = HashMap()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val view = inflater.inflate(R.layout.city_card, parent, false)
        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.model = cities[position]
        val pressure = "Pressure: ${cities[position].main!!.pressure} hPa"
        val humidity = "Humidity: ${cities[position].main!!.humidity}%"
        var sky: String? = "Sky status is unknown"
        if (cities[position].weather!!.isNotEmpty())
            sky = cities[position].weather!![0].description
        holder.city.text = cities[position].name
        holder.pressure.text = pressure
        holder.humidity.text = humidity
        holder.sky.text = sky
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun updateContent(cities: List<WeatherModel>) {
        this.cities.clear()
        this.cities.addAll(cities)
        Log.e("data","${cities.size}")
        for (i in 0 until cities.size) {
            citiesMap[cities[i].name] = i
        }
        notifyDataSetChanged()
    }

    fun addItem(city: WeatherModel) {
        if (!citiesMap.containsKey(city.name)) {
            cities.add(city)
            citiesMap.put(city.name, cities.size - 1)
            notifyItemInserted(cities.size - 1)
        } else {
            val i = citiesMap[city.name]!!
            cities.add(i, city)
            notifyItemChanged(i)
            cities.removeAt(i + 1)
        }
    }

    fun getRawData(): List<WeatherModel> = cities

    inner class WeatherHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var model: WeatherModel? = null
        val city: TextView = v.findViewById(R.id.city_text)
        val humidity: TextView = v.findViewById(R.id.humidity)
        val pressure: TextView = v.findViewById(R.id.pressure)
        val sky: TextView = v.findViewById(R.id.sky)

        init {
            city.setOnClickListener(this)
            humidity.setOnClickListener(this)
            pressure.setOnClickListener(this)
            sky.setOnClickListener(this)
        }

        /** In this way overview is loaded*/
        override fun onClick(v: View?) {
            (this@WeatherAdapter.view as MainActivity).openDetails(model)
        }

    }
}
