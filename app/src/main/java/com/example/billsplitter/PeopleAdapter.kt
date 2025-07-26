package com.example.billsplitter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billsplitter.ItemAdapter.ViewHolder

class PeopleAdapter: RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    private val peopleList = mutableListOf<Person>()

    fun setList(newList: List<Person>) {
        peopleList.clear()
        peopleList.addAll(newList)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.name_text_view)
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.people_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.setText(peopleList[position].name)
    }

    override fun getItemCount() = peopleList.size

}
