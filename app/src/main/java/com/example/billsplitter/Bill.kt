package com.example.billsplitter

data class Bill(val name: String, val subtotal: Double, val tax: Double, val total: Double, var dishes: List<Dish>) {
    fun getTipPercentage(): Double {
        return (total - subtotal - tax) / subtotal
    }

    fun getTaxPercentage(): Double {
        return tax / subtotal
    }

}

