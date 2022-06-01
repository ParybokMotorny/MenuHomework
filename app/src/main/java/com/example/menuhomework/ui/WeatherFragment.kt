package com.example.menuhomework.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.menuhomework.model.database.Request
import com.example.menuhomework.databinding.FragmentWeatherBinding
import com.squareup.picasso.Picasso


// фрагмент для відображення погоди
class WeatherFragment : Fragment() {

    var item: Request? = null
    private var binding: FragmentWeatherBinding? = null

    private val AbsoluteZero = -273.15f

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

    // метод для відображення погоди
    private fun displayWeather(request: Request) {
        binding?.textTemperature?.setText(
            String.format(
                "%f2",
                // температура приходить з кельвінах, тому треба перевести її в цельсії
                request.temp + AbsoluteZero
            )
        )
        binding?.textPressure?.setText(

            String.format(
                "%d",
                request.pressure
            )
        )
        binding?.textHumidity?.setText(
            String.format(
                "%d",
                request.humidity
            )
        )
        binding?.dateEditText?.setText(request.date.toString())

        val uri = Uri.parse("http://openweathermap.org/img/w/${request.icon}.png")

        Picasso.get()
            .load(uri)
            .into(binding?.imageView)
    }

    companion object {
        private const val ITEM = "item"

        @JvmStatic
        fun newInstance(request: Request) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM, request)
                }
            }
    }
}