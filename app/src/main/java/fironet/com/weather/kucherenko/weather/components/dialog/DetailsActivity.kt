package fironet.com.weather.kucherenko.weather.components.dialog

import android.os.Bundle
import fironet.com.weather.kucherenko.weather.R
import fironet.com.weather.kucherenko.weather.abstracts.BaseCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseCompatActivity() {

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_details)
        val data = intent.extras!!.getString("data")
        if (data != null)
            details_text.text = data
        else
            details_text.text = "No data about this city"
    }
}