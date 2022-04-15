package com.example.menuhomework

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menu.data.SearchHistory
import com.example.menu.data.model.WeatherRequest
import com.example.menuhomework.data.SearchHistory
import com.example.menuhomework.data.model.WeatherRequest


class ListAdapter
    (private val activity: Activity) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    var menuPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        // Заполнение элементов холдера
        // Заполнение элементов холдера
        val textElement: TextView = holder.textElement
        textElement.text = SearchHistory.data[position].name

        // Определяем текущую позицию в списке
        // Определяем текущую позицию в списке
        textElement.setOnLongClickListener {
            menuPosition = position
            false
        }


        // Так регистрируется контекстное меню
        activity.registerForContextMenu(textElement)
    }

    override fun getItemCount(): Int {
        return SearchHistory.data.size
    }

    fun addItem(element: String) {
        SearchHistory.data.add(WeatherRequest(element))
        notifyItemInserted(SearchHistory.data.size - 1)
    }

    fun updateItem(position: Int, element: String) {
        SearchHistory.data[position] = WeatherRequest(element)
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        SearchHistory.data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        SearchHistory.data.clear()
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textElement: TextView = itemView.findViewById(R.id.textElement)
    }
}