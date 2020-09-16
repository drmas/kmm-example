package com.urbansportsclub.kmm.androidApp.data

import com.urbansportsclub.kmm.shared.entity.Links
import com.urbansportsclub.kmm.shared.entity.Rocket
import com.urbansportsclub.kmm.shared.entity.RocketLaunch

fun sampleRocketLaunchs() = listOf(
    RocketLaunch(
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
    ),
    RocketLaunch(
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
    ),
    RocketLaunch(
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