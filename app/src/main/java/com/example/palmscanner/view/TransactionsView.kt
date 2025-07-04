package com.example.palmscanner.view

import androidx.compose.runtime.Composable
import com.example.palmscanner.viewmodel.TransactionsViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsView(
    viewModel: TransactionsViewModel = viewModel()
) {
    val selectedTab = remember { mutableStateOf(0) }
    val transactions by viewModel.transactions
    val employees by viewModel.employees
    val isLoading by viewModel.isLoading
    val selectedMonth by viewModel.selectedMonth
    val selectedYear by viewModel.selectedYear

    val monthNames = arrayOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Activity",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tab Selector
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F0F0), RoundedCornerShape(25.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(
                text = "Spending",
                isSelected = selectedTab.value == 0,
                onClick = { selectedTab.value = 0 },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = "Earning",
                isSelected = selectedTab.value == 1,
                onClick = { selectedTab.value = 1 },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Total Circle Display
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = 0.75f,
                modifier = Modifier.size(200.dp),
                strokeWidth = 20.dp,
                color = if (selectedTab.value == 0) Color(0xFF6C63FF) else Color(0xFF6C63FF)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (selectedTab.value == 0) "Total\nSpending" else "Total\nEarning",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = if (selectedTab.value == 0)
                        formatCurrency(viewModel.getTotalSpending())
                    else
                        formatCurrency(viewModel.getTotalEarnings()),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Month Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${monthNames[selectedMonth]} $selectedYear",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = {
                    // You can implement month picker here
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select Month"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transactions List
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (selectedTab.value == 0) {
                    // Spending - Show Employee Salaries
                    items(employees) { employee ->
                        TransactionItem(
                            name = employee.name,
                            amount = employee.salary,
                            date = employee.timestamp?.toDate() ?: Date(),
                            isEarning = false
                        )
                    }
                } else {
                    // Earning - Show Transactions
                    items(transactions) { transaction ->
                        TransactionItem(
                            name = transaction.notes.ifEmpty { "Transaction" },
                            amount = transaction.totalAmount,
                            date = transaction.timestamp?.toDate() ?: Date(),
                            isEarning = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                if (isSelected) Color(0xFF6C63FF) else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun TransactionItem(
    name: String,
    amount: Long,
    date: Date,
    isEarning: Boolean
) {
    val dateFormat = SimpleDateFormat("dd\nMMM\nyyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(date).split("\n")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(50.dp)
            ) {
                Text(
                    text = formattedDate[0],
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = formattedDate[1],
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = formattedDate[2],
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = formatCurrency(amount),
                    fontSize = 14.sp,
                    color = Color(0xFF6C63FF)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .background(
                            Color(0xFFE8F5E8),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Successful",
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50)
                    )
                }
            }

            // Arrow Icon
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "View Details",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

fun formatCurrency(amount: Long): String {
    val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    return format.format(amount).replace("Rp", "IDR")
}