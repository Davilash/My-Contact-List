package com.example.contentprovider.recyclerView

import android.content.Context
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contentprovider.model.ContactModel
import com.example.contentprovider.R
import kotlinx.android.synthetic.main.contact_list.view.*
import java.util.*


class ContactRecyclerAdapter(
    private val context: Context,
    private val contactModel: ArrayList<ContactModel>
) : RecyclerView.Adapter<ViewHolder>() {

    lateinit var tts: TextToSpeech
    private var sendV = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.contact_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contactModel.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactMO = contactModel[position]
        holder.contactName.text = contactMO.contactName
        holder.contactNum.text = contactMO.contactNumber


        tts = TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status != TextToSpeech.ERROR) {
                    tts.language = Locale.US
                }
            })

        holder.contactName.setOnClickListener {
            Toast.makeText(context, "${it.con_name.text}", Toast.LENGTH_SHORT).show()
            sendV = tts.speak(it.con_name.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
            sendV
        }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val contactName: TextView = view.con_name
    val contactNum: TextView = view.con_number
}