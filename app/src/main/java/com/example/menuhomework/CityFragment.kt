package com.example.menuhomework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.menuhomework.data.model.WeatherRequest
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.databinding.FragmentSearchBinding
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CityFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private var binding: FragmentCityBinding? = null
    private var checkCity: Pattern = Pattern.compile("^[A-Z][a-z]{2,}$")
    lateinit var item: WeatherRequest

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refresh = binding?.refresh
        refresh?.setOnClickListener(clickListener)
        binding?.city?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            // Как только фокус потерян, сразу проверяем на валидность данные
            if (hasFocus) return@OnFocusChangeListener
            val tv = v as TextView
            // Это сама валидация, она вынесена в отдельный метод, чтобы не дублировать код
            // см вызов ниже
            validate(tv, checkCity, "Це не місто!")
        }
    }

    private fun validate(tv: TextView, check: Pattern, message: String): Boolean {
        val value = tv.text.toString()
        if (check.matcher(value).matches()) {
            // Проверим на основе регулярных выражений
            hideError(tv)
            return true
        } else {
            showError(tv, message)
            return false
        }
    }

    // Показать ошибку
    private fun showError(view: TextView, message: String) {
        view.error = message
    }

    // спрятать ошибку
    private fun hideError(view: TextView) {
        view.error = null
    }

    var clickListener: View.OnClickListener = View.OnClickListener { // Это сама валидация, она вынесена в отдельный метод, чтобы не дублировать код
        // см вызов ниже
        if(validate(binding?.city as TextView, checkCity, "Це не місто!")) {
            item = WeatherRequest(binding?.city?.text.toString())
//            item = WeatherRequest("some text")
            displayWeather(item)
            giveDataToActivity(item)
        }
    }

    private fun displayWeather(weatherRequest: WeatherRequest) {
        binding?.textTemperature?.setText(
            String.format(
                "%f2",
                weatherRequest.main.temp
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
            String.format(
                "%d",
                weatherRequest.wind.speed
            )
        )
    }

    private fun giveDataToActivity(item: WeatherRequest){
        (activity as MainActivity).onFragmentResult(item)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(weatherRequest: WeatherRequest) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}