package com.dacs.reatimedatabasecrud.repository

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dacs.reatimedatabasecrud.model.Port
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PortRepository {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("ports")

    @RequiresApi(Build.VERSION_CODES.O)
    fun addPort(port: Port) {
        val id = databaseReference.push().key
        port.id = id
        port.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        databaseReference.child(id!!).setValue(port)
    }

    fun getPorts(): LiveData<List<Port>> {
        val portLiveData = MutableLiveData<List<Port>>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val ports: MutableList<Port> = ArrayList()
                dataSnapshot.children.forEach { snapshot ->
                    val port = snapshot.getValue(Port::class.java)
                    ports.add(port!!)
                }
                portLiveData.value = ports
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DatabaseError", error.message)
            }

        })
        return portLiveData
    }

    fun getPortById(portId: String, callback: (Port?) -> Unit) {
        databaseReference.child(portId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val port = snapshot.getValue(Port::class.java)
                callback(port)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }

    fun updatePort(port: Port) {
        databaseReference.child(port.id!!).setValue(port)
    }

    fun deletePort(id: String) {
        databaseReference.child(id).removeValue()
    }
}