package com.example.menuhomework

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        val textElement: TextView = holder.textElement
        textElement.text = SearchHistory.data[position].name
//        textElement.text = "у кожного своя доля і свій шлях широкий той мурує той руйнує той не ситим ком за край світу зазирає чи нема країни щоб загарбать і з собою взять у домовину то тузами обирає свата в його хаті а той нижком у куточку гостить ніж на брата а той тихий та тверезий"

        // Определяем текущую позицию в списке
        textElement.setOnLongClickListener {
            menuPosition = position
            false
        }

        // Так регистрируется контекстное меню
        activity.registerForContextMenu(textElement)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, element: WeatherRequest)
    }

    override fun getItemCount(): Int {
//        return 1
        return SearchHistory.data.size
    }

    fun addItem(element: WeatherRequest) {

        if (SearchHistory.data.find {
                it.name == element.name
            } == null) {
            SearchHistory.data.add(element)
            notifyItemInserted(SearchHistory.data.size - 1)
        }
        else{
            updateItem(SearchHistory.data.size - 1, element)
        }
    }

    fun updateItem(position: Int, element: WeatherRequest) {
        SearchHistory.data[position] = element
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