package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.data.ride.RideUiState
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.RideStringEditAttributeBox
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme


@Composable
fun GettingInLineScreen(
    uiState: RideUiState,
    allRides: List<Ride>,
    updateStateCurrentRide: (Ride?) -> Unit,
    modifier: Modifier
) {

    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var active by rememberSaveable { mutableStateOf(false) }

    val filteredRides = allRides.filter { it.rideName.contains(text, ignoreCase = true) }

    Column (
        modifier = modifier
            .padding(top = 40.dp)
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
                .padding(start = 15.dp, end = 15.dp)
        ) {
            filteredRides.forEach {
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

        if (uiState.currentRide != null){

            //TODO: make call to API to get ride posted wait time

            RideEventInfo(
                currentRide = uiState.currentRide!!,
                onDismiss = { updateStateCurrentRide(null) },
                onConfirm = {}
            )
        }
    }
}

@Composable
fun RideEventInfo(
    currentRide: Ride,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val updatedRide by remember { mutableStateOf(currentRide)}
    Card {
        Column {
            RideStringEditAttributeBox(
                attribute = stringResource(R.string.ride_string),
                onValueChange = { updatedRide.rideName = it },
                attributeValue = updatedRide.rideName,
            )
//            RideIntEditAttributeBox(
//                attribute = stringResource(R.string.api_wait_time_when_entered),
//                onValueChange = { updatedRide.apiPostedTime = it },
//                attributeValue = updatedRide.apiPostedTime
//            )
//            RideIntEditAttributeBox(
//                attribute = stringResource(R.string.posted_time_when_entered),
//                onValueChange = { updatedRide.setRidePostedWaitTime(it) },
//                attributeValue = updatedRide.getRidePostedWaitTime()
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = onConfirm,
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text("Confirm")
                }
            }
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
            modifier = Modifier
        )
    }
}