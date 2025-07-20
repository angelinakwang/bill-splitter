package com.example.billsplitter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.core.view.ViewCompat.requireViewById

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private val billViewModel: BillViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val secondLine: TextView = requireViewById<TextView>(R.id.text)
        val tip: TextView = requireViewById<TextView>(R.id.tip)
        val tax: TextView = requireViewById<TextView>(R.id.tax)

        textView = requireViewById<TextView>(R.id.text_view)
        textView?.setText("name")

        val button : Button = requireViewById(R.id.button)
        button.setOnClickListener {
            val modalBottomSheet = ItemSelectorBottomSheetFragment()
            modalBottomSheet.show(supportFragmentManager, ItemSelectorBottomSheetFragment.TAG)
        }




        tip.setOnClickListener {
            billViewModel.getBillData()
        }
        tax.setOnClickListener {
            billViewModel.getAltBillData()
        }

        val billObserver = Observer<Bill> { bill ->
            secondLine.text = bill.name
            tip.setText(bill.getTipPercentage().toString())
            tax.setText(bill.getTaxPercentage().toString())

        }

        billViewModel.billLiveData.observe(this, billObserver)

    }




}
