package com.example.billsplitter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(personDishMap: MutableMap<Dish, Int>, private val quantityChanged : () -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private val itemList = mutableListOf<Dish>()
    private val dishQuantityMap = personDishMap
    fun setList(newList: List<Dish>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    fun getDishMap() = dishQuantityMap

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val quantityTextView: EditText

        init {
            // Define click listener for the ViewHolder's View
            nameTextView = view.findViewById(R.id.name_text_view)
            quantityTextView = view.findViewById(R.id.quantity_text_view)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.quantityTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dishQuantityMap[itemList[position]] = s.toString().toIntOrNull() ?: 0
                quantityChanged()
            }
        })
        holder.nameTextView.setText(itemList[position].name)
        val quantityText = (dishQuantityMap.get(itemList[position]) ?: 0).toString()
        holder.quantityTextView.setText(quantityText)
    }

    override fun getItemCount() = itemList.size

}