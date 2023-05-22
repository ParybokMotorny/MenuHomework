package com.example.weatherAtTheMoment.features.weather

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherAtTheMoment.model.entity.db.Weather
import com.example.weatherAtTheMoment.databinding.FragmentWeatherBinding
import com.squareup.picasso.Picasso


class WeatherFragment : Fragment() {
    var item: Weather? = null
    private var binding: FragmentWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable(ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item?.let { displayWeather(it) }
    }

    private fun displayWeather(weather: Weather) {
        binding?.textTemperature?.setText(
            String.format(
                "%f2",
                weather.temp + ABSOLUTE_ZERO
            )
        )
        binding?.textPressure?.setText(

            String.format(
                "%d",
                weather.pressure
            )
        )
        binding?.textHumidity?.setText(
            String.format(
                "%d",
                weather.humidity
            )
        )
        binding?.dateEditText?.setText(weather.date.toString())

        val uri = Uri.parse("http://openweathermap.org/img/w/${weather.icon}.png")

        Picasso.get()
            .load(uri)
            .into(binding?.imageView)
    }

    companion object {
        private const val ITEM = "item"
        private const val ABSOLUTE_ZERO = -273.15f

        @JvmStatic
        fun newInstance(weather: Weather) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM, weather)
                }
            }
    }
}