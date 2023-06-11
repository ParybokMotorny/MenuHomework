package com.example.weatherNow.features.map

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
import com.example.weatherNow.R
import com.example.weatherNow.databinding.FragmentMapsBinding
import com.example.weatherNow.model.entity.db.WeatherEntity
import com.example.weatherNow.features.weather.WeatherFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment :
    Fragment(),
    OnMapReadyCallback {

    private var binding: FragmentMapsBinding? = null
    private var currentMarker: Marker? = null
    private var mMap: GoogleMap? = null
    private var currentPosition = LatLng(-34.0, 151.0)

    private lateinit var viewModel: MapsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MapsViewModel::class.java)

        viewModel.getViewState().observe(requireActivity()) { state ->
            state.data?.let {
                renderData(it)
            }
            state.error?.let {
                renderError(it)
            }
        }

        currentPosition = loadPreferences(requireActivity().getPreferences(MODE_PRIVATE))

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestPermissions()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        currentMarker =
            mMap?.addMarker(
                MarkerOptions().position(currentPosition)
                    .title(getString(R.string.current_position))
            )!!
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))

        mMap?.setOnMapLongClickListener {
            savePreferences(requireActivity().getPreferences(MODE_PRIVATE), currentPosition)

            viewModel.loadNote(
                it.latitude.toFloat(),
                it.longitude.toFloat(),
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
        val lat = sharedPref.getFloat(LAT, 0f)
        val lon = sharedPref.getFloat(LON, 0f)

        return LatLng(lat.toDouble(), lon.toDouble())
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestLocation()
        } else {
            requestLocationPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        val locationManager = requireActivity()
            .getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_COARSE

        val provider = locationManager.getBestProvider(criteria, true)
        if (provider != null) {

            locationManager.requestLocationUpdates(provider, 10000, 10f) { location ->
                val lat = location.latitude
                val lng = location.longitude
                currentPosition = LatLng(lat, lng)

                currentMarker?.position = currentPosition
                mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12f))
            }
        }
    }

    private fun requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            )
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }
    }


    private fun renderData(weather: WeatherEntity) {

        val fragment = WeatherFragment.newInstanceFromSearchFragment(weather)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        //viewModel.saveChanges(weather.copyWeather())
    }

    private fun renderError(message: String) {

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
