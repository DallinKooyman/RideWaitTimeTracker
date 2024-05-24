package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.ui.RideHistoryViewModel
import com.dallinkooyman.disneyridetimecomparison.ui.theme.errorContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onErrorContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onSecondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.secondaryContainerDark

@Composable
fun RideHistoryScreen(
    viewModel: RideHistoryViewModel,
    modifier: Modifier = Modifier,
    ) {
    val uiState by viewModel.uiState.collectAsState()
    LazyColumn {
        items(uiState.rideHistory){
            Card (
                colors = CardColors(secondaryContainerDark, onSecondaryContainerDark, errorContainerDark, onErrorContainerDark),
                modifier = Modifier.padding(10.dp)
            ){
                RideEventAttributeBox(
                    rideEvent = it,
                    headlineFontSize = 32.sp,
                    contentFontSize = 20.sp,
                    modifier = modifier
                        .padding(top = 10.dp, start = 35.dp, bottom = 10.dp)
                )
            }

        }
    }
}
