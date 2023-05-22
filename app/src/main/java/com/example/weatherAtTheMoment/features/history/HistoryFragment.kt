package com.example.weatherAtTheMoment.features.history

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherAtTheMoment.R
import com.example.weatherAtTheMoment.features.search.Sortings
import com.example.weatherAtTheMoment.databinding.FragmentSearchBinding
import com.example.weatherAtTheMoment.model.entity.db.Weather
import com.example.weatherAtTheMoment.features.weather.WeatherFragment

class HistoryFragment : Fragment(), RequestRecyclerAdapter.OnItemClickListener {

    private var binding: FragmentSearchBinding? = null
    private lateinit var adapter: RequestRecyclerAdapter
    private lateinit var viewModel: HistoryViewModel

    private var sorting = Sortings.DATEDESC

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
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        initList()
        loadPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initList() {
        val recyclerView = binding?.recyclerList

        recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.layoutManager = layoutManager

        adapter = RequestRecyclerAdapter(requireActivity(), this)

        adapter.itemClickListener = this
        recyclerView?.adapter = adapter

        viewModel.getViewState().observe(requireActivity()) { state ->
            if (state.weather == null) return@observe
            val result = mutableListOf<Weather>()
            for (weather in state.weather) {
                result.add(weather)
            }
            adapter.weathers = result
        }

    }

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
        requireActivity().menuInflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_clear) {
            showDialog("Are you sure that you want to delete all elements?") {
                viewModel.deleteAll()
                adapter.weathers = mutableListOf()
            }
            return true
        } else if (id == R.id.sort_by_name) {
            saveAndSort(Sortings.NAME)
        } else if (id == R.id.sort_by_date) {
            saveAndSort(Sortings.DATE)
        } else if (id == R.id.sort_by_name_descending) {
            saveAndSort(Sortings.NAMEDESC)
        } else if (id == R.id.sort_by_date_descending) {
            saveAndSort(Sortings.DATEDESC)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveAndSort(sort: Int) {
        viewModel.sort(sort)
        sorting = sort
        savePreferences()
    }

    private fun savePreferences() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(SORT, sorting)
        editor.apply()
    }

    private fun loadPreferences() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sorting = sharedPref.getInt(SORT, Sortings.DATEDESC)
        viewModel.sort(sorting)
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
        when (item.itemId) {
            R.id.remove_context -> {
                showDialog("Ви впевнені, що хочете видалити цей елемент?") {
                    viewModel.deleteForId(adapter.weathers[adapter.menuPosition].id)
                    adapter.removeItem(adapter.menuPosition)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(view: View, element: Weather) {
        val fragment = WeatherFragment.newInstance(element)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val SORT = "sort"
    }
}