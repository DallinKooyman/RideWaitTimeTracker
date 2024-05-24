package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.ChangeRideEventInfoDialog
import com.dallinkooyman.disneyridetimecomparison.ui.dialogs.ConfirmDialog
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onPrimaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onSecondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onTertiaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.primaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.secondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.tertiaryContainerDark
import java.time.ZoneId
import java.time.ZonedDateTime

@Composable
fun WaitingInLineScreen(
    rideEvent: RideEvent,
    onChange: (RideEvent) -> Unit,
    onConfirmedOnRide: (RideEvent) -> Unit,
    modifier: Modifier
){
    var showOnRideConfirmDialog by remember { mutableStateOf(false) }

    var showAtInteractableConfirmDialog by remember { mutableStateOf(false) }

    var showChangeRideEventInfoDialog by remember { mutableStateOf(false) }


    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        RideEventAttributeBox(
            rideEvent = rideEvent,
            headlineFontSize = 32.sp,
            contentFontSize = 20.sp,
            modifier = modifier
                .weight(1F)
                .padding(top = 35.dp, start = 35.dp)
        )
        ButtonBox(
            showAtInteractableButton = !rideEvent.hasInteractable,
            onAtInteractableButtonClicked = {
                /* TODO: Calculate time till interactable */
                showAtInteractableConfirmDialog = true
            },
            onChangeRideInfoButtonClicked = {
                showChangeRideEventInfoDialog = true
            },
            onOnRideButtonClicked = {
                showOnRideConfirmDialog = true
            },
            modifier = modifier
                .weight(0.8F)

        )
    }
    if (showAtInteractableConfirmDialog){
        val currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond()
        val waited = (currentTime - rideEvent.enteredLineTime) / 60
        ConfirmDialog(
            dialogTitle = stringResource(R.string.reached_interactable_dialog_title),
            supportingText = "By confirming, ${rideEvent.rideName} will now have an interactable. " +
                    "This will also set the time until interactable for this ride event " +
                    "to ${waited} minutes.",
            onDismiss = { showAtInteractableConfirmDialog = false },
            onConfirm = {
                rideEvent.hasInteractable = true
                rideEvent.timeUntilInteractable = waited.toInt()
                showAtInteractableConfirmDialog = false
                onChange(rideEvent)
            }
        )
    }
    if (showChangeRideEventInfoDialog){
        ChangeRideEventInfoDialog(
            currentRideEvent = rideEvent,
            onDismiss = {
                showChangeRideEventInfoDialog = false
            },
            onConfirm = {
                showChangeRideEventInfoDialog = false
                onChange(rideEvent)
            },
        )
    }
    if (showOnRideConfirmDialog){
        val currentTime = ZonedDateTime.now(ZoneId.systemDefault()).toEpochSecond()
        val waited = (currentTime - rideEvent.enteredLineTime) / 60
        ConfirmDialog(
            dialogTitle = "Are you getting on \n${rideEvent.rideName}?",
            supportingText = "Total Time waited: ${waited} minutes\n\n" +
                    "Confirm only if you are next in line",
            onDismiss = { showOnRideConfirmDialog = false },
            onConfirm = {
                rideEvent.gotOnRideTime = System.currentTimeMillis() / 1000
                rideEvent.timeWaited = waited.toInt()
                onConfirmedOnRide(rideEvent)
                showOnRideConfirmDialog = false
            }
        )
    }
}

@Composable
fun RideEventAttributeBox(
    rideEvent: RideEvent,
    headlineFontSize: TextUnit,
    contentFontSize: TextUnit,
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
                fontSize = headlineFontSize
            )
            RideIntAttributeBox(
                attribute = stringResource(R.string.api_wait_time_when_entered),
                fontSize = contentFontSize,
                value = rideEvent.apiPostedTime
            )
            if (!rideEvent.apiAndPostedTimeAreSame) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.posted_time_when_entered),
                    fontSize = contentFontSize,
                    value = rideEvent.getRidePostedWaitTime()
                )
            }
            RideIntAttributeBox(
                attribute = stringResource(R.string.total_waiting_time),
                fontSize = contentFontSize,
                value = rideEvent.timeWaited
            )
            if (rideEvent.hasInteractable) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.time_waited_to_the_first_interactable),
                    fontSize = contentFontSize,
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
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.padding(bottom = 10.dp)
    ) {
        Row {
            Text(
                text = "$attribute: ",
                fontSize = fontSize,
                lineHeight = 70.sp
            )

            Text(
                text = value,
                fontSize = fontSize,
                lineHeight = 40.sp
            )
        }
    }

}

@Composable
fun RideIntAttributeBox(
    attribute: String,
    value: Int?,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    val displayString = value?.toString() ?: ""
    Box (
        modifier = modifier
    ){
        Row {
            Text(
                text = "$attribute: ",
                fontSize = fontSize
            )
            Text(
                text = displayString,
                fontSize = fontSize
            )
        }
    }
}

@Composable
fun ButtonBox(
    showAtInteractableButton: Boolean = true,
    onAtInteractableButtonClicked: () -> Unit = {},
    onChangeRideInfoButtonClicked: () -> Unit = {},
    onOnRideButtonClicked:() -> Unit = {},
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth(.70f)
            .padding(top = 5.dp)
            .size(70.dp)
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    if (showAtInteractableButton) {
                        onAtInteractableButtonClicked()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = tertiaryContainerDark,
                    contentColor = onTertiaryContainerDark
                ),
                modifier = buttonModifier.alpha(if (showAtInteractableButton) 1F else 0F)
            ) {
                Text(
                    text = stringResource(R.string.at_interactable_button_text),
                    fontSize = 18.sp
                )
            }
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
fun CurrentRideScreenPreview() {
    AppTheme {
        WaitingInLineScreen(
            rideEvent = RideEvent(),
            onChange = {},
            onConfirmedOnRide = {},
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun AttributeBoxPreview() {
    AppTheme {
        RideStringAttributeBox("Ride Name", "Decked Out", 32.sp)
    }
}