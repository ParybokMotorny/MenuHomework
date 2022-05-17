package com.example.menuhomework

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.menuhomework.database.converters.MainToRequestConverter
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.retrofit.model.WeatherRequest
import com.example.menuhomework.interfaces.FragmentCityResult
import com.example.menuhomework.retrofit.Retrofit

private const val ARG_PARAM1 = "param1"
private const val CITY = "city"

// фрагмент для запитів
class CityFragment : Fragment(), Retrofit.OnResponseCompleted {

    private var binding: FragmentCityBinding? = null
    private var listener: FragmentCityResult? = null
    private var showError: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listener = it.getSerializable(ARG_PARAM1) as FragmentCityResult
        }
    }

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
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // зберігаю поточнйи запит
        savePreferences(requireActivity().getPreferences(MODE_PRIVATE))
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        Retrofit(this).run(binding?.city?.text.toString(), BuildConfig.MAPS_API_KEY)
    }

    private fun savePreferences(sharedPref: SharedPreferences){
        val editor = sharedPref.edit()

        editor.putString(CITY, binding?.city?.text.toString())
        showError = true

        editor.apply()
    }

    private fun loadPreferences(sharedPref: SharedPreferences){
        // Для получения настроек Editor не нужен: получаем их прямо из
        // SharedPreferences
        val city = sharedPref.getString(CITY, null)
        binding?.city?.setText(city)
        Retrofit(this).run(binding?.city?.text.toString(), BuildConfig.MAPS_API_KEY)
        showError = false
    }

    // опрацьовую результат роботи ретрофіту
    override fun onCompleted(content: WeatherRequest) {
        val fragment = WeatherFragment.newInstance(MainToRequestConverter.convert(content))

        // фрагмент з відображенням погоди
        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()

        // передаю дані в актівіті
        listener?.onFragmentResult(content)
    }

    // опрацьовую помилку реторофіту
    override fun onFail(message: String) {
        if(showError){
            AlertDialog.Builder(requireContext())
                .setTitle(message)
                .setCancelable(false)
                .setPositiveButton("OK")
                { dialog, id -> }
                .create()
                .show()
        }
        showError = true
    }

    companion object {
        @JvmStatic
        fun newInstance(listener: FragmentCityResult) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, listener)
                }
            }
    }
}