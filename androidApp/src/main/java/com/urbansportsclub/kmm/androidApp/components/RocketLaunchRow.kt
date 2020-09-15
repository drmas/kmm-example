package com.urbansportsclub.kmm.androidApp.components

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.urbansportsclub.kmm.shared.entity.Links
import com.urbansportsclub.kmm.shared.entity.Rocket
import com.urbansportsclub.kmm.shared.entity.RocketLaunch


@Composable
fun RocketLunchRow(rocketLaunch: RocketLaunch) {
    Card (
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Launch name: ${rocketLaunch.missionName}")
            Text(rocketLaunch.launchText, color = rocketLaunch.launchColor)
            Text("Launch year: ${rocketLaunch.launchYear}")
            Text("Launch details: ${rocketLaunch.details.orEmpty()} ")
        }

    }
}

val RocketLaunch.launchText: String
    get() {
        return launchSuccess?.let {
            if (it) "Successful" else "Unsuccessful"
        } ?: "No data"
    }

val RocketLaunch.launchColor: Color
    get() {
        return launchSuccess?.let {
            if (it) Color.Green else Color.Red
        } ?: Color.Gray
    }

@Preview("Successfull")
@Composable
fun previewSuccefful() {
    RocketLunchRow(
        rocketLaunch = RocketLaunch(
            flightNumber = 100,
            missionName = "To Mars",
            launchYear = 2018,
            launchDateUTC = "January 1, 2018, 00:00:00 UTC",
            rocket = Rocket(
                id = "123",
                type = "SpaceShip",
                name = "My Ship"
            ),
            links = Links(
                missionPatchUrl = "",
                articleUrl = ""
            ),
            details = "",
            launchSuccess = true
        )
    )
}

@Preview("Not Successfull")
@Composable
fun previewNotSuccessful() {
    RocketLunchRow(
        rocketLaunch = RocketLaunch(
            flightNumber = 100,
            missionName = "To Mars",
            launchYear = 2018,
            launchDateUTC = "January 1, 2018, 00:00:00 UTC",
            rocket = Rocket(
                id = "123",
                type = "SpaceShip",
                name = "My Ship"
            ),
            links = Links(
                missionPatchUrl = "",
                articleUrl = ""
            ),
            details = "",
            launchSuccess = false
        )
    )
}

@Preview("Not Known")
@Composable
fun previewNotNown() {
    RocketLunchRow(
        rocketLaunch = RocketLaunch(
            flightNumber = 100,
            missionName = "To Mars",
            launchYear = 2018,
            launchDateUTC = "January 1, 2018, 00:00:00 UTC",
            rocket = Rocket(
                id = "123",
                type = "SpaceShip",
                name = "My Ship"
            ),
            links = Links(
                missionPatchUrl = "",
                articleUrl = ""
            ),
            details = "",
            launchSuccess = null
        )
    )
}