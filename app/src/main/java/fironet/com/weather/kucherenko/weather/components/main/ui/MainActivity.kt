package fironet.com.weather.kucherenko.weather.components.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import fironet.com.weather.kucherenko.weather.R
import fironet.com.weather.kucherenko.weather.abstracts.BaseCompatActivity
import fironet.com.weather.kucherenko.weather.components.App
import fironet.com.weather.kucherenko.weather.components.adapters.WeatherAdapter
import fironet.com.weather.kucherenko.weather.components.dialog.DetailsActivity
import fironet.com.weather.kucherenko.weather.components.dialog.Dialog
import fironet.com.weather.kucherenko.weather.components.main.pattern.MainContract
import fironet.com.weather.kucherenko.weather.components.main.pattern.MainPresenter
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import fironet.com.weather.kucherenko.weather.components.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseCompatActivity(), MainContract.View, SwipeRefreshLayout.OnRefreshListener {

    private val presenter: MainPresenter? = MainPresenter()
    private lateinit var adapter: WeatherAdapter

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        adapter = WeatherAdapter(this@MainActivity)
        presenter?.attach(this)

        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(SpaceItemDecoration(3))
        swipe_container.setOnRefreshListener(this)

        fab.setOnClickListener {
            val pop: DialogFragment = Dialog()
            pop.show(supportFragmentManager, "new_city")
        }
        presenter!!.updateWeatherInfo()
    }

    override fun onRefresh() {
        if (!App.instance!!.isNetworkAvailable())
            swipe_container.isRefreshing = false
        presenter!!.update()
    }

    override fun setWeatherInfo(list: List<WeatherModel>) {
        if (swipe_container.isRefreshing)
            swipe_container.isRefreshing = false
        adapter.updateContent(list)
    }

    override fun setNewCity(city: WeatherModel) {
        adapter.addItem(city)
    }

    override fun addNewCity(name: String) {
        if (App.instance!!.isNetworkAvailable())
            presenter!!.requestWeather(name).subscribe()
        else
            Toast.makeText(this, resources.getText(R.string.no_network), Toast.LENGTH_SHORT).show()
    }

    override fun openDetails(city: WeatherModel?) {
        val mIntent = Intent(this, DetailsActivity::class.java)

        if (city != null) {
            val b = Bundle()
            b.putSerializable("data", city.toString())
            mIntent.putExtras(b)
        }

        startActivity(mIntent)
    }

}
