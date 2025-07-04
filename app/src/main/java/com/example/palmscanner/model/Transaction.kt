package com.example.palmscanner.model

data class Transaction(
    val id: String = "",
    val cashier: String = "",
    val customerInfo: String = "",
    val grossProfit: Long = 0,
    val items: List<String> = emptyList(),
    val notes: String = "",
    val outlet: String = "",
    val paymentMethod: String = "",
    val status: String = "",
    val totalAmount: Long = 0,
    val totalCost: Long = 0,
    val transactionNumber: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)
