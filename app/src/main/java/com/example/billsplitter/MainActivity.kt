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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null
    private val billViewModel: BillViewModel by viewModels()
    private lateinit var peopleRecycler: RecyclerView
    private val personAdapter = PeopleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val secondLine: TextView = requireViewById<TextView>(R.id.text)
        val tip: TextView = requireViewById<TextView>(R.id.tip)
        val tax: TextView = requireViewById<TextView>(R.id.tax)
        peopleRecycler = requireViewById<RecyclerView>(R.id.people_layout)


        peopleRecycler.layoutManager = LinearLayoutManager(this)
        peopleRecycler.adapter = personAdapter



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

        val peopleObserver = Observer<List<Person>> { person ->
            personAdapter.setList(person)
        }
        billViewModel.peopleLiveData.observe(this, peopleObserver)

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
                val numberPeople = userNumber.toIntOrNull() ?: 0
                val people = (1..numberPeople).map {
                    Person("Person $it")
                }
                billViewModel.setPersonData(people)
            }
            .setNegativeButton("Cancel", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
