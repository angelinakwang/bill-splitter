package com.example.billsplitter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class BillViewModel : ViewModel() {
    private val _billLiveData = MutableLiveData<Bill>()
    val billLiveData: LiveData<Bill> = _billLiveData
    val dishes = listOf(
        Dish("Diamond Wagyu Set", 392.00, 4),
        Dish("Wagyu Nigiri", 0.00, 4, true)
    )
    val bill = Bill(name = "Wagyu1", subtotal = 392.00, tax = 37.75, total = 500.31, dishes =  dishes)


    val dishes2 = listOf(
        Dish("Dummy Wagyu Set", 392.00, 4, true),
        Dish("Alt Wagyu Nigiri", 300.00, 4)
    )
    val bill2 = Bill(name = "DummyData", subtotal = 853.00, tax = 37.75, total = 1200.31, dishes =  dishes2)

    fun getBillData() {
        _billLiveData.value = bill
    }
    fun getAltBillData() {
        _billLiveData.value = bill2
    }

    fun updateBillDishes(dishes: List<Dish>) {
        bill.dishes = dishes
    }
    fun updateAltBillDishes(dishes: List<Dish>) {
        bill2.dishes = dishes
    }

}