package com.dallinkooyman.disneyridetimecomparison.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme

@Composable
fun ChangeRideEventInfoDialog(
    currentRideEvent: RideEvent,
    onDismiss: () -> Unit,
    onConfirm: (RideEvent) -> Unit,
) {
    val updatedRideEvent by remember { mutableStateOf(currentRideEvent)}

    Dialog(
        onDismissRequest = { /*TODO*/ },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        ) {
        Card {
            Column {
                RideStringEditAttributeBox(
                    attribute = stringResource(R.string.ride_string),
                    onValueChange = { updatedRideEvent.rideName = it },
                    attributeValue = updatedRideEvent.rideName,
                )
                RideIntEditAttributeBox(
                    attribute = stringResource(R.string.api_wait_time_when_entered),
                    onValueChange = { updatedRideEvent.apiPostedTime = it },
                    attributeValue = updatedRideEvent.apiPostedTime
                )
                if (!updatedRideEvent.apiAndPostedTimeAreSame) {
                    RideIntEditAttributeBox(
                        attribute = stringResource(R.string.posted_time_when_entered),
                        onValueChange = { updatedRideEvent.ridePostedWaitTime = it },
                        attributeValue = updatedRideEvent.ridePostedWaitTime
                    )
                }
                RideIntEditAttributeBox(
                    attribute = stringResource(R.string.total_waiting_time),
                    onValueChange = { updatedRideEvent.timeWaited = it },
                    attributeValue = updatedRideEvent.timeWaited
                )
                if (updatedRideEvent.hasInteractable) {
                    RideIntEditAttributeBox(
                        attribute = stringResource(R.string.time_waited_to_the_first_interactable),
                        onValueChange = { updatedRideEvent.timeUntilInteractable = it },
                        attributeValue = updatedRideEvent.timeUntilInteractable
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onConfirm(updatedRideEvent) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }

    }
}

@Composable
fun RideStringEditAttributeBox(
    attribute: String,
    onValueChange: (String) -> Unit,
    attributeValue: String,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf(attributeValue) }
    Box (
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = attribute,
                fontSize = 28.sp,
                softWrap = true,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(.5f)
            )
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it)
                },
                textStyle = TextStyle(fontSize = 22.sp),
                modifier = Modifier.weight(.8f)
            )
        }
    }
}

@Composable
fun RideIntEditAttributeBox(
    attribute: String,
    attributeValue: Int?,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var number by rememberSaveable { mutableStateOf(attributeValue.toString()) }
    if (number == "null"){
        number = ""
    }
    Box (
        modifier = modifier.padding(5.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$attribute: ",
                fontSize = 20.sp,
                softWrap = true,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = number,
                onValueChange = {
                    number = it
                    if (it.isEmpty() || it.toIntOrNull() != null){
                        onValueChange(it.toIntOrNull() ?: -99)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = TextStyle(fontSize = 22.sp),
                modifier = Modifier.weight(.4f)
            )
        }
    }
}


@Preview
@Composable
fun ChangeRideInfoDialogPreview() {
    AppTheme {
        ChangeRideEventInfoDialog(
            currentRideEvent = RideEvent(),
            onDismiss = {},
            onConfirm = {},
        )
    }
}