package com.example.billsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat.requireViewById
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.fragment.app.activityViewModels

class ItemSelectorBottomSheetFragment : BottomSheetDialogFragment() {
    private val billViewModel: BillViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_bottom_sheet_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var data : Int = -1
        if (arguments != null) {
            data = requireArguments().getInt(ItemSelectorBottomSheetFragment.PERSON_ID_KEY)
        }
        val person = billViewModel.peopleLiveData.value!!.getValue(data)

        val dishAdapter = ItemAdapter({ dishes ->
            if (billViewModel.billLiveData.value?.name == "Wagyu1") {
                billViewModel.updateBillDishes(dishes)
            } else {
                billViewModel.updateAltBillDishes(dishes)
            }
        })
        val itemRecycler : RecyclerView = view.findViewById(R.id.itemRecycler)
        itemRecycler.layoutManager = LinearLayoutManager(context)
        itemRecycler.adapter = dishAdapter
        val billNameTextView: TextView = view.requireViewById<TextView>(R.id.bill_name_text_view)

        val billObserver = Observer<Bill> { bill ->
            dishAdapter.setList(bill.dishes)
        }
        billViewModel.billLiveData.observe(this, billObserver)
        billNameTextView.setOnClickListener{
            if (billViewModel.billLiveData.value?.name == "Wagyu1") {
                billViewModel.getAltBillData()
            } else {
                billViewModel.getBillData()
            }
        }
        view.findViewById<Button>(R.id.saveButton).apply {
            setOnClickListener {
                billViewModel.setPerson(person)
                dismiss()
            }
        }

        view.findViewById<Button>(R.id.cancelButton).apply {
            setOnClickListener {
                dismiss()
            }
        }
    }
    companion object {
        const val TAG = "ItemSelector"
        const val PERSON_ID_KEY = "PERSON_ID_KEY"
    }
}