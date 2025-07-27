package com.example.billsplitter

data class Person(val id: Int,val name: String, var dishes: MutableMap<Dish, Int> = mutableMapOf()) {
}