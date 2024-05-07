package com.dacs.reatimedatabasecrud.adapter

import android.app.Application
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dacs.reatimedatabasecrud.R
import com.dacs.reatimedatabasecrud.UpdatePortActivity
import com.dacs.reatimedatabasecrud.model.Port
import com.dacs.reatimedatabasecrud.viewmodel.PortViewModel

class PortAdapter : ListAdapter<Port, PortAdapter.PortViewHolder>(PortDiffCallback()) {
    private lateinit var portViewModel: PortViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.port_item, parent, false)
        portViewModel = ViewModelProvider(parent.context as AppCompatActivity)[PortViewModel::class.java]
        return PortViewHolder(view, portViewModel)
    }

    override fun onBindViewHolder(holder: PortViewHolder, position: Int) {
        val port = getItem(position)
        holder.bind(port)
    }

    class PortViewHolder(itemView: View, private val portViewModel: PortViewModel) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val editBtn: ImageView = itemView.findViewById(R.id.editBtn)
        private val deleteBtn: ImageView = itemView.findViewById(R.id.deleteBtn)

        fun bind(port: Port) {
            titleTextView.text = port.title
            contentTextView.text = port.content
            timeTextView.text = port.createdAt

            editBtn.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, UpdatePortActivity::class.java)
                intent.putExtra("portId", port.id)
                context.startActivity(intent)
            }

            deleteBtn.setOnClickListener {
                portViewModel.deletePort(port.id!!)
            }
        }
    }

    class PortDiffCallback : DiffUtil.ItemCallback<Port>() {
        override fun areItemsTheSame(oldItem: Port, newItem: Port): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Port, newItem: Port): Boolean {
            return oldItem == newItem
        }

    }

}