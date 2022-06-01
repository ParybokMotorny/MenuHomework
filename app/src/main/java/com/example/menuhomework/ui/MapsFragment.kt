package com.example.menuhomework.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.menuhomework.BuildConfig
import com.example.menuhomework.R
import com.example.menuhomework.model.database.converters.MainToRequestConverter
import com.example.menuhomework.databinding.FragmentMapsBinding
import com.example.menuhomework.model.SaveRequest
import com.example.menuhomework.model.retrofit.Retrofit
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import com.example.menuhomework.viewmodels.CityViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment :
    Fragment(),
    OnMapReadyCallback,
    Retrofit.OnResponseCompleted {

    private var binding: FragmentMapsBinding? = null
    private var currentMarker: Marker? = null
    private var mMap: GoogleMap? = null
    private var currentPosition = LatLng(-34.0, 151.0)

    private lateinit var viewModel: CityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CityViewModel::class.java)

        currentPosition = loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestPermissions()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        currentMarker =
            mMap?.addMarker(MarkerOptions().position(currentPosition).title("Текущая позиция"))!!
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))

        mMap?.setOnMapLongClickListener {
            savePreferences(requireActivity().getPreferences(Context.MODE_PRIVATE), currentPosition)

            Retrofit(this)
                .run(
                    it.latitude.toFloat(),
                    it.longitude.toFloat(),
                    "6b0423304b20ad534ccceecc6d3c729a"
                )
        }
    }

    private fun savePreferences(sharedPref: SharedPreferences, currentPosition: LatLng) {
        val editor = sharedPref.edit()

        editor.putFloat(LAT, currentPosition.latitude.toFloat())
        editor.putFloat(LON, currentPosition.longitude.toFloat())

        editor.apply()
    }

    private fun loadPreferences(sharedPref: SharedPreferences): LatLng {
        // Для получения настроек Editor не нужен: получаем их прямо из
        // SharedPreferences
        val lat = sharedPref.getFloat(LAT, 0f)
        val lon = sharedPref.getFloat(LON, 0f)

        return LatLng(lat.toDouble(), lon.toDouble())
    }

    private fun requestPermissions() {
        // Проверим, есть ли Permission’ы, и если их нет, запрашиваем их у
        // пользователя
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Запрашиваем координаты
            requestLocation()
        } else {
            // Permission’ов нет, запрашиваем их у пользователя
            requestLocationPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        // Если Permission’а всё- таки нет, просто выходим: приложение не имеет
        // смысла
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        // Получаем менеджер геолокаций
        val locationManager = requireActivity()
            .getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        // Получаем наиболее подходящий провайдер геолокации по критериям.
        // Но определить, какой провайдер использовать, можно и самостоятельно.
        // В основном используются LocationManager.GPS_PROVIDER или
        // LocationManager.NETWORK_PROVIDER, но можно использовать и
        // LocationManager.PASSIVE_PROVIDER - для получения координат в
        // пассивном режиме
        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {
            // Будем получать геоположение через каждые 10 секунд или каждые
            // 10 метров
            locationManager.requestLocationUpdates(provider, 10000, 10f) { location ->
                val lat = location.latitude // Широта
                val lng = location.longitude // Долгота
                currentPosition = LatLng(lat, lng)

                currentMarker?.position = currentPosition
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12f))
            }
        }
    }

    // Запрашиваем Permission’ы для геолокации
    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            )
        ) {
            // Запрашиваем эти два Permission’а у пользователя
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }


    override fun onCompleted(content: WeatherRequest) {
        val request = MainToRequestConverter.convert(content)

        val fragment = WeatherFragment.newInstance(request)

//        val imm: InputMethodManager =
//            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(requireView().windowToken, 0)

        // фрагмент з відображенням погоди
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        // передаю дані в актівіті
        SaveRequest.saveRequest(request, viewModel)
    }

    override fun onFail(message: String) {

        AlertDialog.Builder(requireContext())
            .setTitle(message)
            .setCancelable(false)
            .setPositiveButton("OK")
            { _, _ -> }
            .create()
            .show()
    }


    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
        private const val LAT = "11"
        private const val LON = "12"
    }
}
