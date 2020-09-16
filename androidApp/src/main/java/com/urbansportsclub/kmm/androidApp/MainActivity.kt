package com.urbansportsclub.kmm.androidApp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.urbansportsclub.kmm.androidApp.states.SpaceXViewModel
import com.urbansportsclub.kmm.androidApp.states.SpaceXViewModelFactory
import com.urbansportsclub.kmm.shared.Greeting
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

        viewModel.loadLaunches(false)
    }

}