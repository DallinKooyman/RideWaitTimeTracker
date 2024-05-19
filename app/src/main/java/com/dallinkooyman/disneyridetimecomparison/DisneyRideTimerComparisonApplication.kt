package com.dallinkooyman.disneyridetimecomparison

import android.app.Application
import com.dallinkooyman.disneyridetimecomparison.data.DisneyRideTimerComparisonContainer
import com.dallinkooyman.disneyridetimecomparison.data.DisneyRideTimerComparisonDataContainer

class DisneyRideTimerComparisonApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: DisneyRideTimerComparisonContainer

    override fun onCreate() {
        super.onCreate()
        container = DisneyRideTimerComparisonDataContainer(this)
    }

}