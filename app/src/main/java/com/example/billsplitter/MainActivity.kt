package com.example.billsplitter

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.core.view.ViewCompat.requireViewById
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class MainActivity : AppCompatActivity() {
    private val billViewModel: BillViewModel by viewModels()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result?.data != null) {
                    val bitmap = result.data?.extras?.get("data") as Bitmap
                    android.util.Log.d("Angelina", "$bitmap")
                    val image = InputImage.fromBitmap(bitmap, 0)
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    recognizer.process(image).addOnSuccessListener { visionText ->
                        val resultText = visionText.text
                        for(block in visionText.textBlocks) {
                            android.util.Log.d("Angelina", "TextblockText ${block.text}")
                        }
                    }
                }
            }
        }
    private var textView: TextView? = null

    private lateinit var peopleRecycler: RecyclerView
    private lateinit var personAdapter: PeopleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val secondLine: TextView = requireViewById<TextView>(R.id.text)
        val tip: TextView = requireViewById<TextView>(R.id.tip)
        val tax: TextView = requireViewById<TextView>(R.id.tax)
        peopleRecycler = requireViewById<RecyclerView>(R.id.people_layout)



        personAdapter = PeopleAdapter(this.supportFragmentManager, ::addPerson)
        peopleRecycler.layoutManager = LinearLayoutManager(this)
        peopleRecycler.adapter = personAdapter



        textView = requireViewById<TextView>(R.id.text_view)
        textView?.setText("name")


        val button : Button = requireViewById(R.id.camera_button)
        button.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            resultLauncher.launch(takePictureIntent)
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

        val peopleObserver = Observer<Map<Int, Person>> { personMap ->
            val personList = personMap.values.toList()
            personAdapter.setList(personList)
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
                billViewModel.clearPersonData()
                (1..numberPeople).forEach {
                    billViewModel.addPersonData(it)
                }
            }
            .setNegativeButton("Cancel", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun addPerson(position: Int) {
        billViewModel.addPersonData(position)
        android.util.Log.d("Angelina", "position $position")
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1

    }




}
