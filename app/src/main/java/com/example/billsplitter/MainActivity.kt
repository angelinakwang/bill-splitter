package com.example.billsplitter

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.core.view.ViewCompat.requireViewById

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private val billViewModel: BillViewModel by viewModels()
    private var selectedPeopleCount: Int = 0
    private lateinit var peopleLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val secondLine: TextView = requireViewById<TextView>(R.id.text)
        val tip: TextView = requireViewById<TextView>(R.id.tip)
        val tax: TextView = requireViewById<TextView>(R.id.tax)
        peopleLayout = requireViewById<LinearLayout>(R.id.people_layout)

        textView = requireViewById<TextView>(R.id.text_view)
        textView?.setText("name")

        val button : Button = requireViewById(R.id.button)
        button.setOnClickListener {
            val modalBottomSheet = ItemSelectorBottomSheetFragment()
            modalBottomSheet.show(supportFragmentManager, ItemSelectorBottomSheetFragment.TAG)
        }

        val selectPersonButton : Button = requireViewById(R.id.person_button)
        selectPersonButton.setOnClickListener{
            showNumberDialog()
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
    private fun showNumberDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder
            .setMessage("Select # of people")
            .setView(input)
            .setTitle("People Selection")
            .setPositiveButton("Done") {_, _ ->
                val userNumber = input.text.toString()
                val numberPeople = userNumber.toIntOrNull()
                selectedPeopleCount = numberPeople ?: 0
                showPeople(selectedPeopleCount)
            }
            .setNegativeButton("Cancel", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showPeople(number: Int) {
        peopleLayout.removeAllViews()
        for ( i in 1..number) {
            val button = Button(this).apply {
                text = "Person $i"
            }
            peopleLayout.addView(button)
        }
    }

}
