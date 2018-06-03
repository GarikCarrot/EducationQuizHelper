package com.garikcarrot.education.fragments.choose

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.garikcarrot.education.R

import java.util.Collections

class ChooseAdapter(private val listener: (String) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    var data = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.choose_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText(data[position], listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val button: Button = itemView.findViewById(R.id.text)

    fun setText(text: String, listener: (String) -> Unit) {
        this.button.text = text
        this.button.setOnClickListener { listener(text) }
    }
}

