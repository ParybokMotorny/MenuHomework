package com.example.menuhomework

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuhomework.adapters.ListAdapter
import com.example.menuhomework.databinding.FragmentSearchBinding
import com.example.menuhomework.data.model.WeatherRequest
import com.google.android.material.snackbar.Snackbar


class SearchFragment : Fragment(), ListAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var binding: FragmentSearchBinding? = null
    private lateinit var adapter: ListAdapter
    private var data: MutableList<WeatherRequest> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

        //встановлюю адаптер
        adapter = ListAdapter(requireActivity())
        initData(adapter)
        data = ArrayList()

        adapter.itemClickListener = this
        recyclerView?.adapter = adapter

    }

    fun receiveData(data: MutableList<WeatherRequest>){
        this.data = data
    }


    private fun initData(adapter: ListAdapter){
        for(it in data){
            adapter.addItem(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        requireActivity().menuInflater.inflate(R.menu.main, menu)

        val search = menu.findItem(R.id.action_search)

        val searchText = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // Реагирует на конец ввода поиска
            override fun onQueryTextSubmit(query: String): Boolean {
                Snackbar.make(searchText, query, Snackbar.LENGTH_LONG).show()
                return true
            }

            // Реагирует на нажатие каждой клавиши
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })
        return
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_clear) {
            val builder = AlertDialog.Builder(requireContext())
            // В билдере указываем заголовок окна (можно указывать как ресурс,
            // так и строку)
            builder.setTitle("Ар ю шуа ебаут зет?") // Указываем сообщение в окне (также есть вариант со строковым параметром)
                .setMessage("2 + 2 = 4?") // Можно указать и пиктограмму
                .setCancelable(false) // Устанавливаем кнопку (название кнопки также можно задавать строкой)
                .setNegativeButton("Ноу айм нот")  // Ставим слушатель, нажатие будем обрабатывать
                { dialog, _ ->
                    Toast.makeText(
                        requireContext(),
                        "Найн",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setPositiveButton("Йес ит из")  // Ставим слушатель, нажатие будем обрабатывать
                { dialog, _ ->
                    Toast.makeText(
                        requireContext(),
                        "Я",
                        Toast.LENGTH_SHORT
                    ).show()

                    adapter.clearItems()
                }
            val alert: AlertDialog = builder.create()
            alert.show()
            return true
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
        val menuInfo = item.menuInfo
        val id = item.itemId
        when (id) {
            R.id.remove_context -> {
                val builder = AlertDialog.Builder(requireContext())
                // В билдере указываем заголовок окна (можно указывать как ресурс,
                // так и строку)
                builder.setTitle("Ар ю шуа ебаут зет?") // Указываем сообщение в окне (также есть вариант со строковым параметром)
                    .setMessage("2 + 2 = 4?") // Можно указать и пиктограмму
                    .setCancelable(false) // Устанавливаем кнопку (название кнопки также можно задавать строкой)
                    .setNegativeButton("Ноу айм нот")  // Ставим слушатель, нажатие будем обрабатывать
                    { dialog, id ->
                        Toast.makeText(
                            requireContext(),
                            "Найн",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .setPositiveButton("Йес ит из")  // Ставим слушатель, нажатие будем обрабатывать
                    { dialog, id ->
                        Toast.makeText(
                            requireContext(),
                            "Я",
                            Toast.LENGTH_SHORT
                        ).show()

                        adapter.removeItem(adapter.menuPosition)
                    }
                val alert: AlertDialog = builder.create()
                alert.show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(view: View, element: WeatherRequest) {
        val fragment = WeatherFragment.newInstance(element)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}