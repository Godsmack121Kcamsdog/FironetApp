package fironet.com.weather.kucherenko.weather.components.dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import fironet.com.weather.kucherenko.weather.R
import fironet.com.weather.kucherenko.weather.components.main.pattern.MainContract
import fironet.com.weather.kucherenko.weather.components.main.ui.MainActivity

class Dialog : DialogFragment() {

    lateinit var cityName: TextView
    lateinit var confirm: Button
    lateinit var parent: MainContract.View

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        parent = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.fragment_new_city, container, false)
        cityName = myView.findViewById(R.id.city_name)
        confirm = myView.findViewById(R.id.button)
        confirm.setOnClickListener {
            parent.addNewCity(cityName.text.toString())
            this@Dialog.dismiss()
        }
        return myView
    }
}