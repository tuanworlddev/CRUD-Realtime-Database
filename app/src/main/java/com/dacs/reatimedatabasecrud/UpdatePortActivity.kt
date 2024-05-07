package com.dacs.reatimedatabasecrud

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dacs.reatimedatabasecrud.model.Port
import com.dacs.reatimedatabasecrud.viewmodel.PortViewModel

class UpdatePortActivity : AppCompatActivity() {
    private lateinit var portViewModel: PortViewModel
    private var portId: String? =  null
    private var port: Port? = null
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var updatePortBtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_port)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)
        updatePortBtn = findViewById(R.id.updatePortBtn)

        portId = intent.getStringExtra("portId")
        portViewModel = ViewModelProvider(this)[PortViewModel::class.java]

        portViewModel.getPortById(portId!!)

        portViewModel.port.observe(this, Observer {
            port = it
            titleEditText.setText(port?.title)
            contentEditText.setText(port?.content)
        })

        updatePortBtn.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            if (title.isNotBlank()) {
                port?.title = title
                port?.content = content
                portViewModel.updatePort(port!!)
                Toast.makeText(this, "Updated port successfully!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}