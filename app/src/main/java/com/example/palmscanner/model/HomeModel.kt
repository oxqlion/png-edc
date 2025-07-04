package com.example.palmscanner.model

import java.text.NumberFormat
import java.util.*

data class BankCard(
    val id: String,
    val bankName: String,
    val cardType: String,
    val balance: Double,
    val accountNumber: String,
    val cardNetwork: String = "Mastercard"
) {
    val formattedBalance: String
        get() = NumberFormat.getNumberInstance(Locale("id", "ID")).format(balance)
}

data class Merchant(
    val id: String,
    val name: String,
    val dealTitle: String,
    val dealDescription: String,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

data class GreenCampaign(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val category: String
)

data class HomeUiState(
    val userName: String = "Leony Smith 🙌",
    val bankCards: List<BankCard> = emptyList(),
    val hotDeals: List<Merchant> = emptyList(),
    val greenCampaigns: List<GreenCampaign> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)