package com.dallinkooyman.disneyridetimecomparison

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dallinkooyman.disneyridetimecomparison.ui.DisneyRideTimeComparisonApp
import com.dallinkooyman.disneyridetimecomparison.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                DisneyRideTimeComparisonApp()
            }
        }
    }
}