package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.ChangeRideEventInfoDialog
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onPrimaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onSecondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.primaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.secondaryContainerDark

@Composable
fun HomeScreen(
    ride: RideEvent?,
    onUpdateRideEventInfo: (RideEvent) -> Unit,
    onOnRideButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (ride == null){
        Text(text = "New Ride Page is under Construction")
    }
    else {
        CurrentRideScreen(
            rideEvent = ride,
            onUpdateRideEventInfo = onUpdateRideEventInfo,
            onOnRideButtonClicked = onOnRideButtonClicked,
            modifier = modifier)
    }
}


@Composable
fun CurrentRideScreen(
    rideEvent: RideEvent,
    onUpdateRideEventInfo: (RideEvent) -> Unit,
    onOnRideButtonClicked: () -> Unit,
    modifier: Modifier
){
    var showChangeRideDialog by remember { mutableStateOf(false) }
    var onChangeRideInfoButtonClicked = {
        showChangeRideDialog = true
    }
    var onDialogDismiss = {
        showChangeRideDialog = false
    }
    var onDialogConfirm: (RideEvent) -> Unit = {
        showChangeRideDialog = false
        onUpdateRideEventInfo(rideEvent)
    }

    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        RideAttributeBox(
            rideEvent = rideEvent,
            modifier = modifier
                .weight(0.6F)
                .padding(top = 35.dp, start = 35.dp)
        )
        ButtonBox(
            onChangeRideInfoButtonClicked = onChangeRideInfoButtonClicked,
            onOnRideButtonClicked = onOnRideButtonClicked,
            modifier = modifier.weight(0.27F)
        )
    }
    if (showChangeRideDialog){
        ChangeRideEventInfoDialog(
            currentRideEvent = rideEvent,
            onDismiss = onDialogDismiss,
            onConfirm = onDialogConfirm,
        )
    }
}

@Composable
fun RideAttributeBox(
    rideEvent: RideEvent,
    modifier: Modifier
){
    Box (
        modifier = modifier
    ){
        //TODO Get current posted wait time from API add after Wait time
        Column {
            RideStringAttributeBox(
                attribute = stringResource(R.string.ride_string),
                value = rideEvent.rideName,
            )
            RideIntAttributeBox(
                attribute = stringResource(R.string.api_wait_time_when_entered),
                value = rideEvent.apiPostedTime
            )
            if (!rideEvent.apiAndPostedTimeAreSame) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.posted_time_when_entered),
                    value = rideEvent.ridePostedWaitTime
                )
            }
            RideIntAttributeBox(
                attribute = stringResource(R.string.total_waiting_time),
                value = rideEvent.timeWaited
            )
            if (rideEvent.hasInteractable) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.time_waited_to_the_first_interactable),
                    value = rideEvent.timeUntilInteractable
                )
            }
        }
    }
}

@Composable
fun RideStringAttributeBox(
    attribute: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {
        Row {
            Text(
                text = "$attribute: ",
                fontSize = 32.sp
            )

            Text(
                text = value,
                fontSize = 32.sp
            )
        }
    }

}

@Composable
fun RideIntAttributeBox(
    attribute: String,
    value: Int?,
    modifier: Modifier = Modifier
) {
    val displayString = value?.toString() ?: ""
    Box (
        modifier = modifier
    ){
        Row {
            Text(
                text = "$attribute: ",
                fontSize = 20.sp
            )
            Text(
                text = displayString,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ButtonBox(
    onChangeRideInfoButtonClicked: () -> Unit = {},
    onOnRideButtonClicked:() -> Unit = {},
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth(.70f)
            .size(70.dp)
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Button(
                onClick = { onChangeRideInfoButtonClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryContainerDark,
                    contentColor = onSecondaryContainerDark
                ),
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.change_ride_button_text),
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { onOnRideButtonClicked() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryContainerDark,
                    contentColor = onPrimaryContainerDark
                ),
                modifier = buttonModifier
            ) {
                Text(
                    text = stringResource(R.string.on_ride_button_text),
                    fontSize = 18.sp
                )

            }
        }
    }
}




@Preview
@Composable
fun HomeScreenPreview() {
    val testRide = RideEvent()
    AppTheme {
        HomeScreen(
            testRide,
            onUpdateRideEventInfo = {},
            onOnRideButtonClicked = {}
        )
    }
}

@Preview
@Composable
fun AttributeBoxPreview() {
    AppTheme {
        RideStringAttributeBox("Ride Name", "Decked Out")
    }
}