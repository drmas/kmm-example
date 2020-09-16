package com.urbansportsclub.kmm.androidApp.components

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.urbansportsclub.kmm.shared.entity.RocketLaunch

@Composable
fun RocketLaunchsListView(rocketLaunchs: State<List<RocketLaunch>?>) {
    LazyColumnFor(items = rocketLaunchs.value ?: listOf()) {
        RocketLunchRow(rocketLaunch = it)
    }
}