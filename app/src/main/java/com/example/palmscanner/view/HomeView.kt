package com.example.palmscanner.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.palmscanner.MainScreen
import com.example.palmscanner.R
import com.example.palmscanner.viewmodel.HomeViewModel
import com.example.palmscanner.model.BankCard
import com.example.palmscanner.model.Merchant
import com.example.palmscanner.model.GreenCampaign
import com.example.palmscanner.ui.theme.PalmScannerTheme

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun HomeView(
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState by homeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A), // Dark blue
                        Color(0xFF3B82F6)  // Light blue
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        // Header Section
        HeaderSection(
            userName = uiState.userName,
            modifier = Modifier.padding(16.dp)
        )

        // Bank Cards Slider
        BankCardsSlider(
            cards = uiState.bankCards,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // PalmPay Guide Section
        PalmPayGuideSection(
            modifier = Modifier.padding(16.dp)
        )

        // Hot Merchant Deals Section
        HotMerchantDealsSection(
            merchants = uiState.hotDeals,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Nearby Merchant Locations
        NearbyMerchantSection(
            modifier = Modifier.padding(16.dp)
        )

        // Green Movements Section
        GreenMovementsSection(
            campaigns = uiState.greenCampaigns,
            modifier = Modifier.padding(16.dp)
        )

        // Bottom padding for navigation bar
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun HeaderSection(
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Good Morning,",
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = userName,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        IconButton(
            onClick = { /* Handle notification click */ }
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = Color.White
            )
        }
    }
}

@Composable
fun BankCardsSlider(
    cards: List<BankCard>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(cards) { card ->
            BankCardItem(card = card)
        }
    }
}

@Composable
fun BankCardItem(card: BankCard) {
    Card(
        modifier = Modifier
            .width(320.dp)
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.bankName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1E3A8A)
                )
                Text(
                    text = card.cardType,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "IDR",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Text(
                text = card.formattedBalance,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.accountNumber,
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                // Placeholder for card logo (Mastercard, Visa, etc.)
                Box(
                    modifier = Modifier
                        .size(40.dp, 24.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.3f),
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun PalmPayGuideSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF3B82F6)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "PalmPay Guide 📚",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Learn how to use PalmPay with ease! Follow our simple steps for a smooth experience 🚀",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }

            // Placeholder for guide illustration
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun HotMerchantDealsSection(
    merchants: List<Merchant>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hot Merchant Deals! 🔥",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            TextButton(
                onClick = { /* Handle see all */ }
            ) {
                Text(
                    text = "See all",
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        Text(
            text = "Discover amazing deals from your favorite merchants! Enjoy exclusive discounts and special offers 🛍️",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(merchants) { merchant ->
                MerchantDealCard(merchant = merchant)
            }
        }
    }
}

@Composable
fun MerchantDealCard(merchant: Merchant) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Placeholder for merchant image
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .background(Color.Gray.copy(alpha = 0.3f))
//            )

            merchant.imageUrl?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = merchant.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }


            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = merchant.dealTitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Text(
                    text = merchant.name,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun NearbyMerchantSection(
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // Adjustable zoom scale - increase for more zoom
    val zoomScale = 5.0f

    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "Nearby Merchant Locations 📍",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "Find the closest merchants where you can register for PalmPay easily!",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Draggable dummy map with custom zoom
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()

                            // Calculate bounds based on zoom scale
                            val maxOffsetX = (size.width * (zoomScale - 1)) / 2
                            val maxOffsetY = (size.height * (zoomScale - 1)) / 2

                            // Update offsets with bounds
                            offsetX = (offsetX + dragAmount.x).coerceIn(-maxOffsetX, maxOffsetX)
                            offsetY = (offsetY + dragAmount.y).coerceIn(-maxOffsetY, maxOffsetY)
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dummy_map),
                    contentDescription = "Dummy Map",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .scale(zoomScale) // Custom zoom scale
                        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                        .fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun GreenMovementsSection(
    campaigns: List<GreenCampaign>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Green Movements 🌱",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Explore more on how we contribute to the environment as a support to the go green movement!",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(campaigns) { campaign ->
                GreenCampaignCard(campaign = campaign)
            }
        }
    }
}

@Composable
fun GreenCampaignCard(campaign: GreenCampaign) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Placeholder for campaign image
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .background(Color.Green.copy(alpha = 0.3f))
//            )

            campaign.imageUrl?.let { painterResource(id = it) }?.let {
                Image(
                    painter = it,
                    contentDescription = campaign.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )
            }


            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = campaign.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Text(
                    text = campaign.description,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    PalmScannerTheme {
        HomeView(homeViewModel = HomeViewModel())
    }
}