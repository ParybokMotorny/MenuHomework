package com.example.menuhomework.ui

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
import com.example.menuhomework.BuildConfig
import com.example.menuhomework.R
import com.example.menuhomework.model.database.converters.MainToRequestConverter
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.model.SaveRequest
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import com.example.menuhomework.model.retrofit.Retrofit
import com.example.menuhomework.viewmodels.CityViewModel

// фрагмент для запитів
class CityFragment : Fragment(), Retrofit.OnResponseCompleted {

    private var binding: FragmentCityBinding? = null
    private var showError: Boolean = false
    private lateinit var viewModel: CityViewModel

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

        // дістаю останній запит запит
        loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))

        viewModel = ViewModelProvider(this).get(CityViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // зберігаю поточнйи запит
        savePreferences(requireActivity().getPreferences(MODE_PRIVATE))
        binding = null
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        Retrofit(this).run(binding?.city?.text.toString(), "6b0423304b20ad534ccceecc6d3c729a")
    }

    private fun savePreferences(sharedPref: SharedPreferences) {
        val editor = sharedPref.edit()

        editor.putString(CITY, binding?.city?.text.toString())
        showError = true

        editor.apply()
    }

    private fun loadPreferences(sharedPref: SharedPreferences) {
        // Для получения настроек Editor не нужен: получаем их прямо из
        // SharedPreferences
        val city = sharedPref.getString(CITY, null)
        binding?.city?.setText(city)
        Retrofit(this).run(binding?.city?.text.toString(), "6b0423304b20ad534ccceecc6d3c729a")
        showError = false
    }

    // опрацьовую результат роботи ретрофіту
    override fun onCompleted(content: WeatherRequest) {

        val request = MainToRequestConverter.convert(content)

        val fragment = WeatherFragment.newInstance(request)

        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        // фрагмент з відображенням погоди
        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()

        SaveRequest.saveRequest(request, viewModel)
        // передаю дані в актівіті
        //listener?.onFragmentResult(content)


    }

    // опрацьовую помилку реторофіту
    override fun onFail(message: String) {
        if (showError) {
            AlertDialog.Builder(requireContext())
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton("OK")
                { _, _ -> }
                .create()
                .show()
        }
        showError = true
    }

    companion object {
        private const val CITY = "city"

    }
}