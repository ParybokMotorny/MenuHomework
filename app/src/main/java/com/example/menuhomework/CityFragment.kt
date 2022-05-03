package com.example.menuhomework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.menuhomework.databinding.FragmentCityBinding
import com.example.menuhomework.data.model.WeatherRequest
import com.example.menuhomework.interfaces.FragmentCityResult
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException
import javax.sql.StatementEventListener

private const val ARG_PARAM1 = "param1"

class CityFragment : Fragment(), Retrofit.OnResponseCompleted {

    private var binding: FragmentCityBinding? = null
    private var listener: FragmentCityResult? = null

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
    }

    private var clickListener: View.OnClickListener = View.OnClickListener {
        Retrofit(this).run(binding?.city?.text.toString(), "6b0423304b20ad534ccceecc6d3c729a")
    }

    // калічно передаємо кожеш об'єкт у актівіті
    private fun giveDataToActivity(item: WeatherRequest) {
        (activity as FragmentCityResult).onFragmentResult(item)
    }

    // опрацбовуэмо результат роботи ретрофіту
    override fun onCompleted(content: WeatherRequest) {
        val fragment = WeatherFragment.newInstance(content)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.weather_container, fragment)
            .commit()

        listener?.onFragmentResult(content)
    }

    // опрацьовуємо помилку реторофіту
    override fun onFail(t: Throwable) {
        if (t.equals(IndexOutOfBoundsException())) {
            AlertDialog.Builder(requireContext())
                .setTitle("This city does not exist")
                .setCancelable(false)
                .setPositiveButton("OK")  // Ставим слушатель, нажатие будем обрабатывать
                { dialog, id -> }
                .create()
                .show()
        }

    }

    companion object{
        @JvmStatic
        fun newInstance(listener: FragmentCityResult) =
            CityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, listener)
                }
            }
    }
}