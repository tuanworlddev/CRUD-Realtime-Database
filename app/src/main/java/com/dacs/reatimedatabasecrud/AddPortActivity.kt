package com.dacs.reatimedatabasecrud

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dacs.reatimedatabasecrud.model.Port
import com.dacs.reatimedatabasecrud.viewmodel.PortViewModel

class AddPortActivity : AppCompatActivity() {
    private lateinit var portViewModel: PortViewModel
    private lateinit var addPortBtn: Button
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_port)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        portViewModel = ViewModelProvider(this)[PortViewModel::class.java]
        addPortBtn = findViewById(R.id.addPortBtn)
        titleEditText = findViewById(R.id.titleEditText)
        contentEditText = findViewById(R.id.contentEditText)

        addPortBtn.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            if (title.isNotBlank()) {
                portViewModel.addPort(Port(title = title, content = content))
            } else {
                Toast.makeText(this@AddPortActivity, "Title is required", Toast.LENGTH_SHORT).show()
            }
        }

        portViewModel.addPortState.observe(this, Observer { addedSuccessfully  ->
            if (addedSuccessfully == true) {
                Toast.makeText(this@AddPortActivity, "Added port successfully", Toast.LENGTH_SHORT).show()
                portViewModel.changeAddState(false)
                titleEditText.text.clear()
                contentEditText.text.clear()
            }
        })
    }
}