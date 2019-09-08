package com.example.contentprovider

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list.view.*

class ContactRecyclerAdapter (private val context: Context, private val contactModel: ArrayList<ContactModel>): RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.contact_list, parent, false))
    }

    override fun getItemCount(): Int {
        return contactModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactMO = contactModel[position]
        holder.contactName.text = contactMO.contactName
        holder.contactNum.text = contactMO.contactNumber
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
    val contactName: TextView = view.con_name
    val contactNum: TextView = view.con_number
}