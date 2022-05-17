package com.example.menuhomework

import android.os.Bundle
import android.os.Message
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuhomework.adapters.RequestRecyclerAdapter
import com.example.menuhomework.databinding.FragmentSearchBinding
import com.example.menuhomework.retrofit.model.WeatherRequest
import com.example.menuhomework.database.App
import com.example.menuhomework.database.Request
import com.example.menuhomework.database.WeatherSource
import com.example.menuhomework.database.dao.WeatherDao
import com.google.android.material.snackbar.Snackbar

// фрагмент з історією пошуку
class SearchFragment : Fragment(), RequestRecyclerAdapter.OnItemClickListener {

    private var binding: FragmentSearchBinding? = null
    private lateinit var adapter: RequestRecyclerAdapter

    // дані які прийдуть з актівіті
    private var data: MutableList<WeatherRequest> = ArrayList()
    private lateinit var weatherSource: WeatherSource

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        registerForContextMenu(binding?.recyclerList as View)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initList() {
        val recyclerView = binding?.recyclerList

        recyclerView?.setHasFixedSize(true)//кажуть так треба писати

        //працюю з вбудованим менеджером
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = layoutManager

        //робота з бд
        val weatherDao: WeatherDao = App.instance.db.educationDao

        weatherSource = WeatherSource(weatherDao)

        adapter = RequestRecyclerAdapter(weatherSource, requireActivity())
        initData(adapter)
        data = ArrayList()

        adapter.itemClickListener = this
        recyclerView?.adapter = adapter

    }

    // отримую дані з актівіті
    fun receiveData(data: MutableList<WeatherRequest>) {
        this.data = data
    }

    // передаю дані в адаптер
    private fun initData(adapter: RequestRecyclerAdapter) {
        for (it in data) {
            adapter.addItem(it)
        }
    }

    // метод для відображення діалогових вікон
    private fun showDialog(message: String, function: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(message)
            .setCancelable(false)
            .setNegativeButton("Ні")
            { _, _ ->
            }
            .setPositiveButton("Так")
            { _, _ ->
                function()
            }
            .create()
            .show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        requireActivity().menuInflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_clear) {
            showDialog("Ви впевнені, що хочете видалити усі елементи?") {
                adapter.clearItems()
            }
            return true
        } else if (id == R.id.sort_by_name) {
            adapter.sortByName(1)
        } else if (id == R.id.sort_by_date) {
            adapter.sortByDate(1)
        } else if (id == R.id.sort_by_name_descending) {
            adapter.sortByName(2)
        } else if (id == R.id.sort_by_date_descending) {
            adapter.sortByDate(2)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.remove_context -> {
                showDialog("Ви впевнені, що хочете видалити цей елемент?") {
                    adapter.removeItem(adapter.menuPosition.toInt())
                }

                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(view: View, element: Request) {
        val fragment = WeatherFragment.newInstance(element)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}