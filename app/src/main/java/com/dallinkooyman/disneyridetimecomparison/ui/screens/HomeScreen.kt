package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.R
import com.dallinkooyman.disneyridetimecomparison.model.Ride
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onPrimaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onSecondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onTertiaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.primaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.secondaryContainerDark
import com.dallinkooyman.disneyridetimecomparison.ui.theme.tertiaryContainerDark

@Composable
fun HomeScreen(
    ride: Ride?,
    modifier: Modifier = Modifier
) {
    if (ride == null){
        Text(text = "New Ride Page is under Construction")
    }
    else {
        CurrentRideScreen(ride = ride, modifier = modifier)
    }
}


@Composable
fun CurrentRideScreen(
    ride: Ride,
    modifier: Modifier
){
    Column (
        modifier = Modifier.fillMaxSize(),
    ){
        RideAttributeBox(
            ride = ride,
            modifier = modifier
                .weight(0.6F)
                .padding(top = 35.dp, start = 35.dp)
        )
        ButtonBox(
            modifier = modifier.weight(0.35F)
        )
    }
}

@Composable
fun RideAttributeBox(
    ride: Ride,
    modifier: Modifier
){
    Box (
        modifier = modifier
    ){
        //TODO Get current posted wait time from API add after Wait time
        Column {
            RideStringAttributeBox(
                attribute = stringResource(R.string.ride_string),
                value = ride.rideName,
            )
            RideIntAttributeBox(
                attribute = stringResource(R.string.wait_time_when_entered),
                value = ride.apiWaitTime
            )
            if (ride.apiWaitTime != ride.ridePostedWaitTime) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.posted_time_when_entered),
                    value = ride.ridePostedWaitTime
                )
            }
            RideIntAttributeBox(
                attribute = stringResource(R.string.total_waiting_time),
                value = ride.totalWaitTime
            )
            if (ride.getTotalTimeUntilFirstInteractable() != null) {
                RideIntAttributeBox(
                    attribute = stringResource(R.string.time_waited_to_the_first_interactable),
                    value = ride.getTotalTimeUntilFirstInteractable()!!
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
    value: Int,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ){
        Row {
            Text(
                text = "$attribute: ",
                fontSize = 20.sp
            )
            Text(
                text = value.toString(),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun ButtonBox(
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
                .height(200.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
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
    val testRide = Ride()
    AppTheme {
        HomeScreen(testRide)
    }
}

@Preview
@Composable
fun AttributeBoxPreview() {
    AppTheme {
        RideStringAttributeBox("Ride Name", "Decked Out")
    }
}