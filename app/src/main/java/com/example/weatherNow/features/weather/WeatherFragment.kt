package com.example.weatherNow.features.weather

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherNow.databinding.FragmentWeatherBinding
import com.example.weatherNow.model.entity.db.WeatherEntity
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Locale


class WeatherFragment : Fragment() {
    var item: WeatherEntity? = null
    var isFromSearchFragment: Boolean = false
    private var binding: FragmentWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable(ITEM)
            isFromSearchFragment = it.getBoolean(IS_FROM_SEARCH_FRAGMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item?.let { displayWeather(it) }
    }

    private fun displayWeather(weather: WeatherEntity) {
        binding?.run {
            val absoluteTemp = weather.temp + ABSOLUTE_ZERO
            val absoluteFeelsLike = weather.feelsLike + ABSOLUTE_ZERO
            temperature.text = String.format("%.2f", absoluteTemp)
            feelsLike.text = String.format("%.2f", absoluteFeelsLike)
            pressure.text = weather.pressure.toString()
            humidity.text = weather.humidity.toString()
            description.text = weather.description
            windSpeed.text = weather.windSpeed.toString()

            if (isFromSearchFragment) {
                dateTextView.visibility = View.GONE
                date.visibility = View.GONE
            } else {
                val dateFormat = SimpleDateFormat("MM/dd HH:mm", Locale.getDefault())
                val formattedDate = weather.date?.let { dateFormat.format(it) }
                date.text = formattedDate
            }

            val uri = Uri.parse("http://openweathermap.org/img/w/${weather.icon}.png")
//            val imageUrl = "http://openweathermap.org/img/w/04d.png"

            Picasso.get().load(uri).into(imageView)
        }
    }

    companion object {
        private const val ITEM = "item"
        private const val IS_FROM_SEARCH_FRAGMENT = "IS_FROM_SEARCH_FRAGMENT"
        private const val ABSOLUTE_ZERO = -273.15f

        @JvmStatic
        fun newInstanceFromSearchFragment(weather: WeatherEntity) = WeatherFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM, weather)
                putBoolean(IS_FROM_SEARCH_FRAGMENT, true)
            }
        }

        @JvmStatic
        fun newInstanceFromHistoryFragment(weather: WeatherEntity) = WeatherFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM, weather)
                putBoolean(IS_FROM_SEARCH_FRAGMENT, false)
            }
        }
    }
}