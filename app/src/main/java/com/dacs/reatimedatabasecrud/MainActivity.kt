package com.dacs.reatimedatabasecrud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dacs.reatimedatabasecrud.adapter.PortAdapter
import com.dacs.reatimedatabasecrud.viewmodel.PortViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var portViewModel: PortViewModel
    private lateinit var addPortBtn: FloatingActionButton
    private lateinit var portRecyclerView: RecyclerView
    private lateinit var portAdapter: PortAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        portViewModel = ViewModelProvider(this)[PortViewModel::class.java]
        addPortBtn = findViewById(R.id.addPortBtn)
        portRecyclerView = findViewById(R.id.portRecyclerView)

        portAdapter = PortAdapter()

        portRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = portAdapter
        }

        portViewModel.ports.observe(this, Observer { ports ->
            portAdapter.submitList(ports.reversed())
        })
        addPortBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddPortActivity::class.java))
        }
    }
}