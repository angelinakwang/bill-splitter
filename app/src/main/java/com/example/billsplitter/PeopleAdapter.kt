package com.example.billsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billsplitter.ItemAdapter.ViewHolder

class PeopleAdapter(private val fragManager: FragmentManager): RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {
    private val peopleList = mutableListOf<Person>()
    private val peopleClickListener = OnClickListener { view ->
        val position = view.getTag() as Int

        val bundle = Bundle().apply {
            putInt(ItemSelectorBottomSheetFragment.PERSON_ID_KEY, peopleList[position].hashCode())
        }
        val fragment = ItemSelectorBottomSheetFragment()
        fragment.isCancelable = false
        fragment.arguments = bundle
        fragment.show(fragManager, ItemSelectorBottomSheetFragment.TAG)
    }

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
        holder.nameTextView.setTag(position)
        holder.nameTextView.setOnClickListener(peopleClickListener)
    }

    override fun getItemCount() = peopleList.size
}
