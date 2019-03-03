package fironet.com.weather.kucherenko.weather.components.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import fironet.com.weather.kucherenko.weather.R
import fironet.com.weather.kucherenko.weather.abstracts.BaseCompatActivity
import fironet.com.weather.kucherenko.weather.components.adapters.WeatherAdapter
import fironet.com.weather.kucherenko.weather.components.dialog.DetailsActivity
import fironet.com.weather.kucherenko.weather.components.dialog.Dialog
import fironet.com.weather.kucherenko.weather.components.main.pattern.MainContract
import fironet.com.weather.kucherenko.weather.components.main.pattern.MainPresenter
import fironet.com.weather.kucherenko.weather.components.models.WeatherModel
import fironet.com.weather.kucherenko.weather.components.utils.SpaceItemDecoration
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseCompatActivity(), MainContract.View {

    private val presenter: MainPresenter? = MainPresenter()
    private lateinit var adapter: WeatherAdapter

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        adapter = WeatherAdapter(this@MainActivity)
        presenter?.attach(this)
        fab.setOnClickListener {
            val pop: DialogFragment = Dialog()
            pop.show(supportFragmentManager, "new_city")
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(SpaceItemDecoration(3))

        val list = ArrayList<String>()
        list.add("London")
        list.add("Kiev")
        list.add("Warshava")
        list.add("Berlin")
        list.add("Oslo")
        list.add("Canada")

        presenter!!.requestWeather(list)
    }

    override fun setWeatherInfo(list: List<WeatherModel>) {
        adapter.updateContent(list)
    }

    override fun addNewCity(name: String) {
        presenter!!.requestWeather(name).onErrorResumeNext { throwable: Throwable ->
            Flowable.empty<WeatherModel>()
        }.doOnNext {
            if (it.name !== null)
                adapter.addItem(it)
        }.subscribe()
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
