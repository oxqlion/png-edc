package com.example.palmscanner.viewmodel

import androidx.compose.runtime.mutableStateOf
import com.example.palmscanner.model.Employee
import com.example.palmscanner.model.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query

class TransactionsViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    private val _transactions = mutableStateOf<List<Transaction>>(emptyList())
    val transactions: State<List<Transaction>> = _transactions

    private val _employees = mutableStateOf<List<Employee>>(emptyList())
    val employees: State<List<Employee>> = _employees

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _selectedMonth = mutableStateOf(Calendar.getInstance().get(Calendar.MONTH))
    val selectedMonth: State<Int> = _selectedMonth

    private val _selectedYear = mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: State<Int> = _selectedYear

    init {
        loadTransactions()
        loadEmployees()
    }

    fun updateSelectedMonth(month: Int, year: Int) {
        _selectedMonth.value = month
        _selectedYear.value = year
        loadTransactions()
        loadEmployees()
    }

    private fun loadTransactions() {
        _isLoading.value = true
        println("DEBUG: Starting to load transactions...")

        // First, try to get ALL transactions without date filtering
        firestore.collection("transactions")
            .get()
            .addOnSuccessListener { documents ->
                println("DEBUG: Successfully connected to Firestore")
                println("DEBUG: Found ${documents.size()} transactions in the database")

                if (documents.isEmpty) {
                    println("DEBUG: ⚠️ NO TRANSACTIONS DATA FOUND IN DATABASE")
                    println("DEBUG: The 'transactions' collection is empty or doesn't exist")
                } else {
                    println("DEBUG: ✅ Transactions data found, processing...")

                    val transactionsList = documents.map { doc ->
                        println("DEBUG: Processing transaction: ${doc.id}")
                        println("DEBUG: Transaction data: ${doc.data}")

                        Transaction(
                            id = doc.id,
                            cashier = "invalid reference",
                            customerInfo = "invalid reference",
                            grossProfit = doc.getLong("grossProfit") ?: 0,
                            items = doc.get("items") as? List<String> ?: emptyList(),
                            notes = doc.getString("notes") ?: "",
                            outlet = "invalid reference",
                            paymentMethod = doc.getString("paymentMethod") ?: "",
                            status = doc.getString("status") ?: "",
                            totalAmount = doc.getLong("totalAmount") ?: 0,
                            totalCost = doc.getLong("totalCost") ?: 0,
                            transactionNumber = "invalid reference",
                            timestamp = null
                        )
                    }
                    _transactions.value = transactionsList
                    println("DEBUG: ✅ Successfully loaded ${transactionsList.size} transactions")
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                println("DEBUG: ❌ FAILED TO CONNECT TO FIRESTORE DATABASE")
                println("DEBUG: Connection error: ${exception.message}")
                println("DEBUG: Exception type: ${exception.javaClass.simpleName}")

                // Check specific error types
                when {
                    exception.message?.contains("PERMISSION_DENIED") == true -> {
                        println("DEBUG: 🔒 PERMISSION DENIED - Check your Firestore security rules")
                    }
                    exception.message?.contains("UNAVAILABLE") == true -> {
                        println("DEBUG: 🌐 NETWORK UNAVAILABLE - Check your internet connection")
                    }
                    exception.message?.contains("NOT_FOUND") == true -> {
                        println("DEBUG: 📂 COLLECTION NOT FOUND - The 'transactions' collection doesn't exist")
                    }
                    else -> {
                        println("DEBUG: 🔧 OTHER ERROR - ${exception.message}")
                    }
                }

                _isLoading.value = false
            }
    }

    private fun loadEmployees() {
        println("DEBUG: Starting to load employees...")

        // First, try to get ALL employees without date filtering
        firestore.collection("employees")
            .get()
            .addOnSuccessListener { documents ->
                println("DEBUG: Successfully connected to Firestore for employees")
                println("DEBUG: Found ${documents.size()} employees in the database")

                if (documents.isEmpty) {
                    println("DEBUG: ⚠️ NO EMPLOYEES DATA FOUND IN DATABASE")
                    println("DEBUG: The 'employees' collection is empty or doesn't exist")
                } else {
                    println("DEBUG: ✅ Employees data found, processing...")

                    val employeesList = documents.map { doc ->
                        println("DEBUG: Processing employee: ${doc.id}")
                        println("DEBUG: Employee data: ${doc.data}")

                        Employee(
                            id = doc.id,
                            name = doc.getString("name") ?: "",
                            salary = doc.getLong("salary") ?: 0,
                            position = doc.getString("role") ?: "",
                            timestamp = null
                        )
                    }
                    _employees.value = employeesList
                    println("DEBUG: ✅ Successfully loaded ${employeesList.size} employees")
                }
            }
            .addOnFailureListener { exception ->
                println("DEBUG: ❌ FAILED TO CONNECT TO FIRESTORE DATABASE (EMPLOYEES)")
                println("DEBUG: Connection error: ${exception.message}")
                println("DEBUG: Exception type: ${exception.javaClass.simpleName}")

                // Check specific error types
                when {
                    exception.message?.contains("PERMISSION_DENIED") == true -> {
                        println("DEBUG: 🔒 PERMISSION DENIED - Check your Firestore security rules")
                    }
                    exception.message?.contains("UNAVAILABLE") == true -> {
                        println("DEBUG: 🌐 NETWORK UNAVAILABLE - Check your internet connection")
                    }
                    exception.message?.contains("NOT_FOUND") == true -> {
                        println("DEBUG: 📂 COLLECTION NOT FOUND - The 'employees' collection doesn't exist")
                    }
                    else -> {
                        println("DEBUG: 🔧 OTHER ERROR - ${exception.message}")
                    }
                }
            }
    }

    fun getTotalEarnings(): Long {
        return transactions.value.sumOf { it.totalAmount }
    }

    fun getTotalSpending(): Long {
        return employees.value.sumOf { it.salary }
    }
}