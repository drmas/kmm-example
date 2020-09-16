package com.urbansportsclub.kmm.androidApp.states

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.urbansportsclub.kmm.shared.SpaceXSDK
import com.urbansportsclub.kmm.shared.cache.DatabaseDriverFactory
import com.urbansportsclub.kmm.shared.entity.RocketLaunch
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


sealed class UIState {
    class Loading : UIState()
    class Loaded : UIState()
    class Error(val error: Throwable) : UIState()
}

class SpaceXViewModel(val sdk: SpaceXSDK) : ViewModel() {
    private val mainScope = MainScope()

    val rocketLaunchs = MutableLiveData(listOf<RocketLaunch>())
    val state = MutableLiveData<UIState>(UIState.Loading())

    fun loadLaunches(forceReload: Boolean) {
        state.value = UIState.Loading()
        mainScope.launch {
            kotlin.runCatching {
                sdk.getLaunches(forceReload)
            }.onSuccess {
                rocketLaunchs.value = it
                state.value = UIState.Loaded()
                Log.d("Loading", "Setting loading off in uistate")
            }.onFailure {
                state.value = UIState.Error(it)
                state.value = UIState.Loaded()
            }
        }
    }
}

class SpaceXViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val sdk = SpaceXSDK(DatabaseDriverFactory(context))
        return SpaceXViewModel(sdk) as T
    }

}