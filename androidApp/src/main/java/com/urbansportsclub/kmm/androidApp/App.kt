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
import com.urbansportsclub.kmm.androidApp.data.sampleRocketLaunchs
import com.urbansportsclub.kmm.androidApp.states.SpaceXViewModel
import com.urbansportsclub.kmm.androidApp.states.UIState
import com.urbansportsclub.kmm.shared.SpaceXSDK
import com.urbansportsclub.kmm.shared.cache.DatabaseDriverFactory

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
    val rocketLaunchs = viewModel.rocketLaunchs.observeAsState()
    val isEmpty = rocketLaunchs.value?.isEmpty() ?: false
    val isLoading = status.value is UIState.Loading
    val hasError = status.value is UIState.Error

    when {
        hasError -> {
            Text(text = (status.value as UIState.Error).error.message.orEmpty() )
        }
        isLoading && isEmpty -> {
            Loading()
        }
        else -> {
            SwipeToRefreshLayout(
                refreshingState = isLoading,
                onRefresh = {
                    viewModel.loadLaunches(false)
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
    viewModel.rocketLaunchs.value = sampleRocketLaunchs()

    App(viewModel =  viewModel)
}