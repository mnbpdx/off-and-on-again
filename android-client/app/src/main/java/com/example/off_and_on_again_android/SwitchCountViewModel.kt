package com.example.off_and_on_again_android

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

const val SWITCH_ID = "603c51e024be826bad81e9f2"

class SwitchCountViewModel : ViewModel() {

    private var _flipCount = MutableLiveData<Int>()
    val flipCount: LiveData<Int>
        get() = _flipCount

    private val _switchResponse = MutableLiveData<SwitchResponse>()
    val switchResponse: LiveData<SwitchResponse>
        get() = _switchResponse

    fun setupSocketConnection(): Socket? {
        return try {
            IO.socket("http://192.168.1.23:5000")
        } catch (e: URISyntaxException) { null }
    }

    fun getSwitchCountRealTime(activity: FragmentActivity) {
        val listener = Emitter.Listener { args ->
            activity.runOnUiThread(
                Runnable {
                    val data = args[0] as JSONObject
                    try {
                        _flipCount.value = data.getJSONObject("switch").getInt("countFlips")
                    } catch (e: JSONException) {
                        return@Runnable
                    }
                }
            )
        }
    }

    fun getSwitchCount(switchId: String = SWITCH_ID) {
        viewModelScope.launch {
            _switchResponse.value = RaspberryPiApi.retrofitService.getSwitch(switchId)
            _flipCount.value = switchResponse.value?.switch?.countFlips
        }
    }
}
