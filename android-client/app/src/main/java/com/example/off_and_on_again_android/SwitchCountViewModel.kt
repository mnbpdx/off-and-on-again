package com.example.off_and_on_again_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

const val SWITCH_ID = "603c51e024be826bad81e9f2"

class SwitchCountViewModel : ViewModel() {

    private var _flipCount = MutableLiveData<Int>()
    val flipCount: LiveData<Int>
        get() = _flipCount

    private val _switchResponse = MutableLiveData<SwitchResponse>()
    val switchResponse: LiveData<SwitchResponse>
        get() = _switchResponse

    fun getSwitchCount(switchId: String = SWITCH_ID) {
        viewModelScope.launch {
            _switchResponse.value = RaspberryPiApi.retrofitService.getSwitch(switchId)
            _flipCount.value = switchResponse.value?.switch?.countFlips
        }
    }
}
