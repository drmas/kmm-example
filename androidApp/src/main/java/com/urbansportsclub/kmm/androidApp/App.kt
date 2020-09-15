package com.urbansportsclub.kmm.androidApp

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.urbansportsclub.kmm.androidApp.components.RocketLaunchsListView
import com.urbansportsclub.kmm.androidApp.components.SwipeToRefreshLayout
import com.urbansportsclub.kmm.shared.SpaceXSDK
import com.urbansportsclub.kmm.shared.cache.DatabaseDriverFactory
import com.urbansportsclub.kmm.shared.entity.Links
import com.urbansportsclub.kmm.shared.entity.Rocket
import com.urbansportsclub.kmm.shared.entity.RocketLaunch

@Composable
fun App(viewModel: SpaceXViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SpaceX Launches") },
            )
        },
        backgroundColor = Color.LightGray
    )
    {
        ListWithLoading(viewModel)
    }
}

@Composable
fun ListWithLoading(viewModel: SpaceXViewModel) {
    val status = viewModel.state.observeAsState()
    val rocketLaunchs = viewModel.rocketLaunchs.observeAsState(initial = listOf())
    val isLoading = status.value is UIState.Loading

    if (isLoading) {
        Loading()
    } else {
        SwipeToRefreshLayout(
            refreshingState = isLoading,
            onRefresh = {
                viewModel.loadLaunches(true)
            },
            refreshIndicator = {
                Surface(elevation = 10.dp, shape = CircleShape) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .preferredSize(36.dp)
                            .padding(6.dp),
                        strokeWidth = 3.dp
                    )
                }
            },
        )
        { RocketLaunchsListView(rocketLaunchs = rocketLaunchs) }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalGravity = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun previewLoading() {
    val context = ContextAmbient.current
    val sdk = SpaceXSDK(DatabaseDriverFactory(context))
    val viewModel = SpaceXViewModel(sdk)

    App(viewModel = viewModel)
}

@Preview
@Composable
fun previewWithData() {
    val context = ContextAmbient.current
    val sdk = SpaceXSDK(DatabaseDriverFactory(context))
    val viewModel = SpaceXViewModel(sdk)
    viewModel.state.value = UIState.Loaded()
    viewModel.rocketLaunchs.value = listOf(
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

    App(viewModel = viewModel)
}