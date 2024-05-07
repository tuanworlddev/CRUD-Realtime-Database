package com.dacs.reatimedatabasecrud.viewmodel

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dacs.reatimedatabasecrud.model.Port
import com.dacs.reatimedatabasecrud.repository.PortRepository

class PortViewModel(application: Application) : AndroidViewModel(application) {
    private val portRepository = PortRepository()
    val ports: LiveData<List<Port>> = portRepository.getPorts()

    private val _port = MutableLiveData<Port>()
    val port: LiveData<Port>
        get() = _port

    private val _addPortState = MutableLiveData<Boolean>()
    val addPortState: LiveData<Boolean>
        get() = _addPortState

    fun changeAddState(state: Boolean) {
        _addPortState.value = state
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPort(port: Port) {
        portRepository.addPort(port)
        _addPortState.value = true
    }

    fun getPortById(portId: String) {
        portRepository.getPortById(portId) { port ->
            _port.postValue(port)
        }
    }

    fun updatePort(port: Port) {
        portRepository.updatePort(port)
    }

    fun deletePort(id: String) {
        portRepository.deletePort(id)
    }
}