package com.example.menuhomework.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.menuhomework.model.database.converters.MainToRequestConverter.convert
import com.example.menuhomework.R
import com.example.menuhomework.ui.Search.Sortings
import com.example.menuhomework.model.retrofit.model.WeatherRequest
import com.example.menuhomework.model.database.Request
import com.example.menuhomework.model.database.WeatherSource


// Адаптер для RecyclerView
class RequestRecyclerAdapter(
    private val activity: Activity
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
        val students: List<Request> = WeatherSource.requests.value!!
        val student = students[position]
        holder.requestCity.text = student.city
        holder.studentDate.text = student.date.toString()

        itemClickListener?.let { holder.setOnClickListener(it, WeatherSource.requests.value!![position]) }

        // Тут определяем, какой пункт меню был нажат
        holder.cardView.setOnLongClickListener { view: View? ->
            menuPosition = position.toLong()
            false
        }

        // Регистрируем контекстное меню
        activity.registerForContextMenu(holder.cardView)
    }

    override fun getItemCount(): Int {
        return WeatherSource.countRequests.toInt()
    }

    class ViewHolder(var cardView: View) : RecyclerView.ViewHolder(cardView) {
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

        WeatherSource.addRequest(convert(element))
        notifyItemInserted((WeatherSource.requests.value?.size ?: 1) - 1)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(requests: List<Request>) {
        //SearchHistory.data[position] = element
        clearItems()
        WeatherSource.saveRequests(requests)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        //SearchHistory.data.removeAt(position)
        val requestForRemove = WeatherSource
            .requests.value?.get(position)
        WeatherSource.removeRequest(requestForRemove!!.id)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        //SearchHistory.data.clear()
        WeatherSource.clearRequests()
        notifyDataSetChanged()
    }

//    fun sortByName(isAsc: Int){
//        dataSource.sortByName(isAsc)
//        notifyDataSetChanged()
//    }
//
//    fun sortByDate(isAsc: Int){
//        dataSource.sortByDate(isAsc)
//        notifyDataSetChanged()
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun sort(sortings: Int) {
        when (sortings) {
            Sortings.DATE -> WeatherSource.sortByDate(1)
            Sortings.DATEDESC -> WeatherSource.sortByDate(2)
            Sortings.NAME -> WeatherSource.sortByName(1)
            Sortings.NAMEDESC -> WeatherSource.sortByName(2)
        }

        notifyDataSetChanged()
    }
}
