package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dallinkooyman.disneyridetimecomparison.model.RideEvent
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme
import com.dallinkooyman.disneyridetimecomparison.ui.theme.onPrimaryDark


@Composable
fun GettingInLineScreen(
    modifier: Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var active by rememberSaveable { mutableStateOf(false) }

    val rides = listOf("Roller Coaster", "Ferris Wheel", "Log Flume", "Bumper Cars", "Haunted House")
    val filteredRides = rides.filter { it.contains(text, ignoreCase = true) }

    Column (
        modifier = modifier.padding(top = 40.dp).fillMaxWidth()
    ){
        DockedSearchBar(
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
                    headlineContent = { Text(text = it) },
                    leadingContent = { Text(text = "25") },
                    modifier = Modifier
                        .clickable {
                            text = it
                            active = false
                        }
                )
            }
        }

        RecentRideColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
        )
    }
}

@Composable
fun RecentRideColumn(
    modifier: Modifier
){
    Text(
        text = "Most Recent Rides",
        fontSize = 28.sp,
        modifier = Modifier.padding(top = 30.dp, start = 35.dp, bottom = 10.dp)
    )
    LazyColumn (
        contentPadding = PaddingValues(bottom = 10.dp),
        modifier = modifier
            .height(300.dp)
            .background(color = onPrimaryDark, shape = RoundedCornerShape(26.dp))
    ){
        items(5) {
            RecentRideBox(modifier = Modifier)
        }
    }
}



@Composable
fun RecentRideBox(
    modifier: Modifier
){
    Box (
        modifier = modifier
    ) {
        val tempRideEvent = RideEvent()
        tempRideEvent.timeWaited = 8
        tempRideEvent.apiPostedTime = 15
        tempRideEvent.hasInteractable = true
        tempRideEvent.timeUntilInteractable = 5
        tempRideEvent.apiAndPostedTimeAreSame = false
        tempRideEvent.setRidePostedWaitTime(10)
        RideAttributeBox(
            rideEvent = tempRideEvent,
            headlineFontSize = 26.sp,
            contentFontSize = 14.sp,
            modifier = Modifier
                .padding(top = 15.dp, start = 30.dp, bottom = 10.dp, end = 30.dp)

        )
    }
}

@Preview
@Composable
fun GettingInLineScreenPreview(){
    AppTheme {
        GettingInLineScreen(modifier = Modifier)
    }
}