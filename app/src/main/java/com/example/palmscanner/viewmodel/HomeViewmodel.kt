package com.example.palmscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.palmscanner.R
import com.example.palmscanner.model.BankCard
import com.example.palmscanner.model.GreenCampaign
import com.example.palmscanner.model.HomeUiState
import com.example.palmscanner.model.Merchant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Load mock data
                val bankCards = getMockBankCards()
                val hotDeals = getMockHotDeals()
                val greenCampaigns = getMockGreenCampaigns()

                _uiState.value = _uiState.value.copy(
                    bankCards = bankCards,
                    hotDeals = hotDeals,
                    greenCampaigns = greenCampaigns,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun getMockBankCards(): List<BankCard> {
        return listOf(
            BankCard(
                id = "1",
                bankName = "BCA",
                cardType = "DEBIT CARD",
                balance = 8480504.0,
                accountNumber = "Account 786 - 453 - 0743",
                cardNetwork = "Mastercard"
            ),
            BankCard(
                id = "2",
                bankName = "BNI",
                cardType = "CREDIT CARD",
                balance = 5250000.0,
                accountNumber = "Account 123 - 456 - 0891",
                cardNetwork = "Visa"
            ),
            BankCard(
                id = "3",
                bankName = "Mandiri",
                cardType = "DEBIT CARD",
                balance = 3150000.0,
                accountNumber = "Account 987 - 654 - 3210",
                cardNetwork = "Mastercard"
            )
        )
    }

    private fun getMockHotDeals(): List<Merchant> {
        return listOf(
            Merchant(
                id = "1",
                name = "KFC",
                dealTitle = "BUY 5 GET 3 CHICKEN HOT & CRISPY FREE!",
                dealDescription = "Get extra crispy chicken with amazing deals",
                imageUrl = R.drawable.hot1
            ),
            Merchant(
                id = "2",
                name = "Burger King",
                dealTitle = "BUY 1 GET 1 BEEF BURGER FREE!",
                dealDescription = "Double the taste, double the fun",
                imageUrl = R.drawable.hot2
            ),
            Merchant(
                id = "3",
                name = "McDonald's",
                dealTitle = "3 PACK NUGGETS FOR ONLY 25K!",
                dealDescription = "Crispy nuggets at unbeatable price",
                imageUrl = R.drawable.hot3
            ),
//            Merchant(
//                id = "4",
//                name = "Pizza Hut",
//                dealTitle = "BUY 2 GET 1 PIZZA FREE!",
//                dealDescription = "Share the joy with friends and family"
//            )
        )
    }

    private fun getMockGreenCampaigns(): List<GreenCampaign> {
        return listOf(
            GreenCampaign(
                id = "1",
                title = "Tarik Cts. Green Indonesia",
                description = "Hari ini di Pameran Hari Lingkungan Hidup...",
                category = "Environment",
                imageUrl = R.drawable.green1
            ),
            GreenCampaign(
                id = "2",
                title = "KELAKASA",
                description = "Masih dalam semangat memperingati Hari Lingkungan...",
                category = "Sustainability",
                imageUrl = R.drawable.green2
            ),
            GreenCampaign(
                id = "3",
                title = "Pilum yang Mendaur Ulang",
                description = "Sisa yang berguna tak lagi mengotori tanah Indonesia...",
                category = "Recycling",
                imageUrl = R.drawable.green3
            ),
//            GreenCampaign(
//                id = "4",
//                title = "Clean Water Initiative",
//                description = "Bringing clean water to remote areas",
//                category = "Water"
//            )
        )
    }

    fun refreshData() {
        loadInitialData()
    }

    fun onMerchantDealClick(merchantId: String) {
        // Handle merchant deal click
        // Navigate to merchant detail or show deal details
    }

    fun onGreenCampaignClick(campaignId: String) {
        // Handle green campaign click
        // Navigate to campaign detail or show more information
    }
}