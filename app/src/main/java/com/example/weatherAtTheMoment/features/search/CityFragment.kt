package com.example.weatherAtTheMoment.features.search

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.weatherAtTheMoment.R
import com.example.weatherAtTheMoment.databinding.FragmentCityBinding
import com.example.weatherAtTheMoment.model.database.Weather
import com.example.weatherAtTheMoment.features.weather.WeatherFragment

class CityFragment : Fragment() {

    private var binding: FragmentCityBinding? = null
    private var showError: Boolean = false
    private lateinit var viewModel: CityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val refresh = binding?.refresh
        refresh?.setOnClickListener(clickListener)

        viewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        viewModel.getViewState().observe(requireActivity()) { state ->
            state.data?.let {
                renderData(it)
            }
            state.error?.let {
                renderError(it)
            }
        }

        loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        savePreferences(requireActivity().getPreferences(MODE_PRIVATE))
        binding = null
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        viewModel.loadNote(binding?.city?.text.toString())
    }

    private fun savePreferences(sharedPref: SharedPreferences) {
        val editor = sharedPref.edit()

        editor.putString(CITY, binding?.city?.text.toString())
        showError = true

        editor.apply()
    }

    private fun loadPreferences(sharedPref: SharedPreferences) {
        val city = sharedPref.getString(CITY, null)
        binding?.city?.setText(city)
        viewModel.loadNote(binding?.city?.text.toString())
        showError = false
    }

    private fun renderData(weather: Weather) {
        val fragment = WeatherFragment.newInstance(weather)

        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()

        viewModel.saveChanges(weather.copyWeather())
    }

    private fun renderError(error: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(error)
            .setCancelable(false)
            .setPositiveButton("OK")
            { _, _ -> }
            .create()
            .show()
    }

    companion object {
        private const val CITY = "city"
    }
}