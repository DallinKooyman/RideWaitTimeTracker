package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.Constants
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.ConfirmDialog
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.RideIntEditAttributeBox
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.RideStringEditAttributeBox
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme
import com.dallinkooyman.disneyridetimecomparison.ui.theme.errorContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.errorContainerDarkHighContrast
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onErrorContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onPrimaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onSecondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.primaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.surfaceContainerHighDark
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime


@Composable
fun GettingInLineScreen(
    uiState: RideUiState,
    allRides: List<Ride>,
    updateStateCurrentRide: (Ride?) -> Unit,
    onConfirmInLine: (RideEvent) -> Unit,
    modifier: Modifier
) {

    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var active by rememberSaveable { mutableStateOf(false) }

    val filteredRides = allRides.filter { it.rideName.contains(text, ignoreCase = true) }

    Column (
        modifier = modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ){
        SearchBar(
            query = text,
            onQueryChange = {text = it},
            onSearch = {expanded = false},
            active = active,
            onActiveChange = {
                active = it
            },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            placeholder = { Text(text = "Search for a ride") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            LazyColumn {
                items(filteredRides) {
                    ListItem(
                        headlineContent = { Text(text = it.rideName) },
                        leadingContent = { Text(text = it.apiWaitTime.toString()) },
                        modifier = Modifier
                            .clickable {
                                active = false
                                updateStateCurrentRide(allRides.find { ride: Ride -> it.rideName == ride.rideName })
                            }
                    )
                }
            }
        }

        if (uiState.currentRide != null){

            //TODO: make call to API to get ride posted wait time

            val rideEvent by remember { mutableStateOf(RideEvent())}
            rideEvent.rideName = uiState.currentRide!!.rideName
            rideEvent.rideId = uiState.currentRide!!.id
            rideEvent.apiPostedTime = null

            var showConfirmRideEventDialog by rememberSaveable { mutableStateOf(false)}
            var showCancelRideEventDialog by rememberSaveable { mutableStateOf(false)}

            RideEventInfo(
                currentRide = uiState.currentRide!!,
                rideEvent = rideEvent
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 15.dp, end = 15.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                              showCancelRideEventDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorContainerDarkHighContrast,
                        contentColor = errorContainerDark
                    ),
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(.5f),
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Button(
                    onClick = {
                              showConfirmRideEventDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryContainerDark,
                        contentColor = onPrimaryContainerDark
                    ),
                    modifier = Modifier
                        .padding(15.dp)
                        .weight(.5f),
                ) {
                    Text(
                        text = "Confirm",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            if (showConfirmRideEventDialog){
                ConfirmDialog(
                    dialogTitle = "Getting in line for ${rideEvent.rideName}?",
                    supportingText = "By pressing you confirm you are stepping in line for" +
                            " ${rideEvent.rideName} at ${LocalTime.now().format(Constants.TIME_HOUR_FORMAT)}",
                    onDismiss = { showConfirmRideEventDialog = false },
                    onConfirm = {
                        rideEvent.enteredLineTime = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond()
                        rideEvent.apiPostedTime = uiState.currentRide!!.apiWaitTime
                        rideEvent.hasInteractable = uiState.currentRide!!.hasInteractable
                        onConfirmInLine(rideEvent)
                    }
                )
            }
        }
    }
}

@Composable
fun RideEventInfo(
    currentRide: Ride,
    rideEvent: RideEvent
) {
    val updatedRide by remember { mutableStateOf(currentRide)}

    Card (
        colors = CardColors(surfaceContainerHighDark, onSecondaryContainerDark, errorContainerDark, onErrorContainerDark),
        modifier = Modifier.padding(top = 70.dp, start = 20.dp, end = 20.dp)
    ) {
        Column (
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
        ) {
            RideStringEditAttributeBox(
                attribute = stringResource(R.string.ride_string),
                onValueChange = {},
                attributeValue = updatedRide.rideName,
                attributeFontSize = 23.sp,
                attributeValueFontSize = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )
            RideIntEditAttributeBox(
                attribute = stringResource(R.string.api_wait_time),
                attributeValue = currentRide.apiWaitTime, //TODO Get Api wait time
                onValueChange = {},
                attributeFontSize = 20.sp,
                attributeValueFontSize = 18.sp,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth()
            )
            RideIntEditAttributeBox(
                attribute = stringResource(R.string.posted_time_when_entered),
                onValueChange = {
                    if (it != -99){
                        rideEvent.setRidePostedWaitTime(it)
                    }
                },
                attributeValue = rideEvent.apiPostedTime,
                attributeFontSize = 20.sp,
                attributeValueFontSize = 18.sp,
                readOnly = false,
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth()
            )
        }
    }
}



//        if (showGettingInLineDialog){
//            ConfirmDialog(
//                dialogTitle = "In Line for ${rideName}?",
//                supportingText = "You are getting in line for ${rideName}",
//                onDismiss = {
//                    showGettingInLineDialog = false
//                },
//                onConfirm = {
//
//                }
//            )
//        }
//        TODO: Remove
//        RecentRideColumn(modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
//        )
//@Composable
//fun RecentRideColumn(
//    modifier: Modifier
//){
//    Text(
//        text = "Most Recent Rides",
//        fontSize = 28.sp,
//        modifier = Modifier.padding(top = 30.dp, start = 35.dp, bottom = 10.dp)
//    )
//    LazyColumn (
//        contentPadding = PaddingValues(bottom = 10.dp),
//        modifier = modifier
//            .height(300.dp)
//            .background(color = onPrimaryDark, shape = RoundedCornerShape(26.dp))
//    ){
//        items(5) {
//            RecentRideBox(modifier = Modifier)
//        }
//    }
//}
//
//
//
//@Composable
//fun RecentRideBox(
//    modifier: Modifier
//){
//    Box (
//        modifier = modifier
//    ) {
//        val tempRideEvent = RideEvent()
//        tempRideEvent.timeWaited = 8
//        tempRideEvent.apiPostedTime = 15
//        tempRideEvent.hasInteractable = true
//        tempRideEvent.timeUntilInteractable = 5
//        tempRideEvent.apiAndPostedTimeAreSame = false
//        tempRideEvent.setRidePostedWaitTime(10)
//        RideEventAttributeBox(
//            rideEvent = tempRideEvent,
//            headlineFontSize = 26.sp,
//            contentFontSize = 14.sp,
//            modifier = Modifier
//                .padding(top = 15.dp, start = 30.dp, bottom = 10.dp, end = 30.dp)
//
//        )
//    }
//}

@Preview
@Composable
fun GettingInLineScreenPreview(){
    AppTheme {
        GettingInLineScreen(
            uiState = RideUiState(),
            updateStateCurrentRide = { Ride() },
            allRides = listOf(),
            onConfirmInLine = {},
            modifier = Modifier
        )
    }
}