package com.example.off_and_on_again_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject
import java.net.URISyntaxException

const val SWITCH_ID = "603c51e024be826bad81e9f2"

class SwitchCountViewModel : ViewModel() {

    private var _flipCount = MutableLiveData<Int>()
    val flipCount: LiveData<Int>
        get() = _flipCount

    private var _isOn = MutableLiveData<Boolean>()
    val isOn: LiveData<Boolean>
        get() = _isOn

    fun setupSocketConnection(): Socket? {
        return try {
            IO.socket("http://192.168.1.23:5000")
        } catch (e: URISyntaxException) {
            null
        }
    }

    fun updateFlipCount(data: JSONObject) {
        _flipCount.value = data.getInt("countFlips")
        _isOn.value = data.getBoolean("isOn")
    }
//    //Need to add back HTTP request functionality, need it for initial setup
//    fun getSwitchCount(switchId: String = SWITCH_ID) {
//        viewModelScope.launch {
//            _switchResponse.value = RaspberryPiApi.retrofitService.getSwitch(switchId)
//            _flipCount.value = switchResponse.value?.switch?.countFlips
//        }
//    }
}
