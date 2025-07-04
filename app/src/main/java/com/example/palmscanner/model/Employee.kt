package com.example.palmscanner.model

data class Employee(
    val id: String = "",
    val name: String = "",
    val salary: Long = 0,
    val position: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)
