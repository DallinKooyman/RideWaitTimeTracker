package com.dallinkooyman.disneyridetimecomparison

import java.time.format.DateTimeFormatter

object Constants {
    val TIME_HOUR_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val DISNEY_LAND_RESORT_ID = "bfc89fd6-314d-44b4-b89e-df1a89cf991e"
    val DISNEY_LAND_PARK_ID = "7340550b-c14d-4def-80bb-acdb51d49a66"
    val CALIFORNIA_RESORT_ID = "832fcd51-ea19-4e77-85c7-75d5843b127c"
    val TRANSITION_TIME = 150
}