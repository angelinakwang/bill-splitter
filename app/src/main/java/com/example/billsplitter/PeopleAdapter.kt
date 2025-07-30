package com.example.billsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billsplitter.ItemAdapter.ViewHolder

class PeopleAdapter(private val fragManager: FragmentManager, private val addPerson : (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val peopleList = mutableListOf<Person>()
    private val peopleClickListener = OnClickListener { view ->
        val position = view.getTag() as Int

        val bundle = Bundle().apply {
            putInt(ItemSelectorBottomSheetFragment.PERSON_ID_KEY, peopleList[position].id)
        }
        val fragment = ItemSelectorBottomSheetFragment()
        fragment.isCancelable = false
        fragment.arguments = bundle
        fragment.show(fragManager, ItemSelectorBottomSheetFragment.TAG)
    }
    private val addPeopleClickListener = OnClickListener { view ->
        val position = view.getTag() as Int
        addPerson(position)



    }


    fun setList(newList: List<Person>) {
        peopleList.clear()
        peopleList.addAll(newList)
        notifyDataSetChanged()
    }


    class PersonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView

        init {
                nameTextView = view.findViewById(R.id.name_text_view)
        }
    }

    class AddButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addPersonButtonView: Button

        init {
            addPersonButtonView = view.findViewById(R.id.add_person_button_view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < peopleList.size) {
            PERSON_VIEW_TYPE
        } else {
            ADD_BUTTON_VIEW_TYPE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PERSON_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.people_row, parent, false)
                PersonViewHolder(view)
            }
            ADD_BUTTON_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.add_button_layout, parent, false)
                AddButtonViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid View")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            PERSON_VIEW_TYPE -> {
                val peopleHolder = holder as PersonViewHolder
                peopleHolder.nameTextView.setText(peopleList[position].name)
                peopleHolder.nameTextView.setTag(position)
                peopleHolder.nameTextView.setOnClickListener(peopleClickListener)
            }
            ADD_BUTTON_VIEW_TYPE -> {
                val buttonHolder = holder as AddButtonViewHolder
                buttonHolder.addPersonButtonView.setTag(position + 1)
                buttonHolder.addPersonButtonView.setOnClickListener(addPeopleClickListener)
            }
        }

    }

    override fun getItemCount() = peopleList.size + 1

    companion object {
        const val PERSON_VIEW_TYPE = 1
        const val ADD_BUTTON_VIEW_TYPE = 2
    }
}
