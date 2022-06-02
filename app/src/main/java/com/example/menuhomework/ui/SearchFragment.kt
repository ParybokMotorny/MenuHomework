package com.example.menuhomework.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuhomework.R
import com.example.menuhomework.ui.Search.Sortings
import com.example.menuhomework.databinding.FragmentSearchBinding
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import com.example.menuhomework.model.database.App
import com.example.menuhomework.model.database.Request
import com.example.menuhomework.model.database.WeatherSource
import com.example.menuhomework.viewmodels.SearchViewModel


// фрагмент з історією пошуку
class SearchFragment : Fragment(), RequestRecyclerAdapter.OnItemClickListener {

    private var binding: FragmentSearchBinding? = null
    private lateinit var adapter: RequestRecyclerAdapter
    private lateinit var viewModel: SearchViewModel

    // дані які прийдуть з актівіті
    private var data: MutableList<WeatherRequest> = ArrayList()
    private lateinit var weatherSource: WeatherSource

    private var sorting = Sortings.DATE

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

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        initList()

        loadPreferences()
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

        adapter = RequestRecyclerAdapter(requireActivity())
        initData(adapter)
        data = ArrayList()

        adapter.itemClickListener = this
        recyclerView?.adapter = adapter

        viewModel.viewState().observe(requireActivity()) { state ->
            adapter.update(state.requests)
        }

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
        adapter.sort(sort)
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
        adapter.sort(sorting)
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

    companion object {
        private const val SORT = "sort"
    }
}