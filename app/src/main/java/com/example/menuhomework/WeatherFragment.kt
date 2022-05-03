package com.example.menuhomework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.menuhomework.data.model.WeatherRequest
import com.example.menuhomework.databinding.FragmentWeatherBinding

private const val ARG_PARAM1 = "param1"

class WeatherFragment : Fragment() {

    var item: WeatherRequest? = null
    private var binding: FragmentWeatherBinding? = null
    private val AbsoluteZero = -273.15f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getSerializable(ARG_PARAM1) as WeatherRequest
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

    private fun displayWeather(weatherRequest: WeatherRequest) {
        binding?.textTemperature?.setText(
            String.format(
                "%f2",
                weatherRequest.main.temp + AbsoluteZero
            )
        )
        binding?.textPressure?.setText(

            String.format(
                "%d",
                weatherRequest.main.pressure
            )
        )
        binding?.textHumidity?.setText(
            String.format(
                "%d",
                weatherRequest.main.humidity
            )
        )
        binding?.textWindspeed?.setText(
                weatherRequest.wind.speed.toString()
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: WeatherRequest) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}