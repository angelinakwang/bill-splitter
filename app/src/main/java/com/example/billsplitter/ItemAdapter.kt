package com.example.billsplitter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(val updateViewModel: (List<Dish>) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val itemList = mutableListOf<Dish>()

    fun setList(newList: List<Dish>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val quantityTextView: TextView
        val checkBoxView: CheckBox

        init {
            // Define click listener for the ViewHolder's View
            nameTextView = view.findViewById(R.id.name_text_view)
            quantityTextView = view.findViewById(R.id.quantity_text_view)
            checkBoxView = view.findViewById(R.id.checkbox_text_view)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.setText(itemList[position].name)
        holder.quantityTextView.setText(itemList[position].quantity.toString())
        holder.checkBoxView.isChecked = itemList[position].isChecked
        holder.checkBoxView.setOnCheckedChangeListener { checkBoxView, isChecked ->
            itemList[position].isChecked = isChecked
            android.util.Log.d("Angelina", "Item got checked $isChecked")
        }
        updateViewModel(itemList)
    }

    override fun getItemCount() = itemList.size
}