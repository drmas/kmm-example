package com.urbansportsclub.kmm.androidApp

import android.app.Application
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.urbansportsclub.kmm.shared.Greeting
import com.urbansportsclub.kmm.shared.SpaceXSDK
import com.urbansportsclub.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: SpaceXViewModel by viewModels { SpaceXViewModelFactory(application) }

        setContent {
            App(viewModel = viewModel)
        }

        viewModel.loadLaunches(true)
    }

}