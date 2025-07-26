package com.example.billsplitter

data class Person(var name: String, val dishes: MutableList<Int> = mutableListOf()) {
}