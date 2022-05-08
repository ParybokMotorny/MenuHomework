package com.example.menuhomework.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menuhomework.database.converters.MainToRequestConverter.convert
import com.example.menuhomework.R
import com.example.menuhomework.retrofit.model.WeatherRequest
import com.example.menuhomework.database.Request
import com.example.menuhomework.database.WeatherSource


// Адаптер для RecyclerView
class RequestRecyclerAdapter(
// Источник данных
    val dataSource: WeatherSource, private val activity: Activity
) :
    RecyclerView.Adapter<RequestRecyclerAdapter.ViewHolder>() {

    // Позиция в списке, на которой было нажато меню
    var menuPosition: Long = 0
        private set
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Заполняем данными записи на экране
        val students: List<Request> = dataSource.requests
        val student = students[position]
        holder.requestCity.text = student.city
        holder.studentDate.text = student.date.toString()

        itemClickListener?.let { holder.setOnClickListener(it,dataSource.requests[position]) }

        // Тут определяем, какой пункт меню был нажат
        holder.cardView.setOnLongClickListener { view: View? ->
            menuPosition = position.toLong()
            false
        }

        // Регистрируем контекстное меню
        activity.registerForContextMenu(holder.cardView)
    }

    override fun getItemCount(): Int {
        return dataSource.countRequests.toInt()
    }

    class ViewHolder(var cardView: View)
        : RecyclerView.ViewHolder(cardView) {
        var requestCity: TextView = cardView.findViewById(R.id.textRequestDate)
        var studentDate: TextView = cardView.findViewById(R.id.textRequestCity)

        fun setOnClickListener(listener: OnItemClickListener, request: Request) {
            this.cardView.setOnClickListener {
                val adapterPosition = adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onItemClick(it, request)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, element: Request)
    }

    fun addItem(element: WeatherRequest) {

        //SearchHistory.data.add(element)

        dataSource.addRequest(convert(element))
        notifyItemInserted(dataSource.requests.size - 1)

    }

    fun updateItem(position: Int, element: WeatherRequest) {
        //SearchHistory.data[position] = element
        dataSource.updateRequest(convert(element))
        notifyItemRemoved(position)
    }

    fun removeItem(position: Int) {
        //SearchHistory.data.removeAt(position)
        val requestForRemove = dataSource
            .requests[position]
        dataSource.removeRequest(requestForRemove.id)
        notifyItemRemoved(position)
    }

    fun clearItems() {
        //SearchHistory.data.clear()
        dataSource.clearRequests()
        notifyDataSetChanged()
    }
}
