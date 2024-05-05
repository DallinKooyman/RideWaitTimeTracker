package com.dallinkooyman.disneyridetimecomparison.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {

    Column {
            AttributeBox(attribute = "Name", value = "DeckedOut")
    }
}

@Composable
fun AttributeBox(
    attribute: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box {
        Row {
            Text(text = attribute + ":" + value)
        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun AttributeBoxPreview() {
    AppTheme {
        AttributeBox("Ride Name", "Decked Out")
    }
}