package com.example.billsplitter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class BillViewModel : ViewModel() {
    private val _peopleLiveData = MutableLiveData<MutableMap<Int, Person>>(mutableMapOf())
    val peopleLiveData: LiveData<MutableMap<Int, Person>> = _peopleLiveData

    private val _billLiveData = MutableLiveData<Bill>()
    val billLiveData: LiveData<Bill> = _billLiveData
    val dishes = listOf(
        Dish("Diamond Wagyu Set", 7.50, 2),
        Dish("Wagyu Nigiri", 12.00, 1),
    )
    val bill = Bill(name = "Wagyu1", subtotal = 27.00, tax = 2.23, total = 29.23, dishes =  dishes)


    val dishes2 = listOf(
        Dish("Dummy Wagyu Set", 392.00, 4),
        Dish("Alt Wagyu Nigiri", 300.00, 4)
    )
    val bill2 = Bill(name = "DummyData", subtotal = 853.00, tax = 37.75, total = 1200.31, dishes =  dishes2)

    fun clearPersonData() {
        _peopleLiveData.value?.clear()
        _peopleLiveData.value = _peopleLiveData.value
    }
    fun addPersonData(position: Int) {
        val person = Person(id = position, "Person $position")
        _peopleLiveData.value?.put(person.id, person)
        _peopleLiveData.value = _peopleLiveData.value
    }
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