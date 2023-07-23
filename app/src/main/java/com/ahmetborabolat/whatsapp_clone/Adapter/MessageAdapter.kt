package com.ahmetborabolat.whatsapp_clone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahmetborabolat.whatsapp_clone.R
import com.ahmetborabolat.whatsapp_clone.databinding.ReceiverLayoutItemBinding
import com.ahmetborabolat.whatsapp_clone.databinding.SendItemLayoutBinding
import com.ahmetborabolat.whatsapp_clone.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MessageAdapter(var context : Context, var list : ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ITEM_SEND = 1
    var ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ITEM_SEND)
            SendViewHolder(
                LayoutInflater.from(context).inflate(R.layout.send_item_layout,parent,false)
            )
        else ReceiverViewHolder(
            LayoutInflater.from(context).inflate(R.layout.receiver_layout_item,parent,false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SEND else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        if(holder.itemViewType == ITEM_SEND){
            val viewHolder = holder as SendViewHolder
            viewHolder.binding.userMessage.text = message.message
        }else{
            val viewHolder = holder as ReceiverViewHolder
            viewHolder.binding.userMessage.text = message.message
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SendViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = SendItemLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var binding = ReceiverLayoutItemBinding.bind(view)
    }
}