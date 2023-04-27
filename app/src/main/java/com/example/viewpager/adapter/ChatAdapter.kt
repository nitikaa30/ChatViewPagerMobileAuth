package com.example.viewpager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager.databinding.User1Binding
import com.example.viewpager.databinding.User2Binding
import com.example.viewpager.model.ChatData

class ChatAdapter(private var items: List<ChatData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_LEFT) {
            val binding = User1Binding.inflate(inflater, parent, false)
            LeftViewHolder(binding)
        } else {
            val binding = User2Binding.inflate(inflater, parent, false)
            RightViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is LeftViewHolder) {
            holder.bind(item)
        } else if (holder is RightViewHolder) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return if (item.senderId) {
            VIEW_TYPE_LEFT
        } else {
            VIEW_TYPE_RIGHT
        }
    }

    class LeftViewHolder(private val binding: User1Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatData) {
            // Bind data to views in left-aligned layout
            binding.sender.text = item.text
            binding.timestamp.text= item.timestamp.toString()
        }
    }

    class RightViewHolder(private val binding: User2Binding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatData) {
            // Bind data to views in right-aligned layout
            binding.sender.text = item.text
            binding.timestamp.text= item.timestamp.toString()
        }
    }
        fun setMessages(messages: List<ChatData>) {
        this.items=messages
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_LEFT = 0
        private const val VIEW_TYPE_RIGHT = 1
    }
}


//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.viewpager.databinding.User1Binding
//import com.example.viewpager.databinding.User2Binding
//import com.example.viewpager.model.ChatData
//import com.example.viewpager.model.ChatMessageModel
//import com.firebase.ui.firestore.FirestoreRecyclerOptions
//import com.google.firebase.firestore.FirebaseFirestore
//
//class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
//
//    private var messages: List<ChatMessageModel> = emptyList()
//    private var messagess: List<ChatMessageModel> = emptyList()
//    private lateinit var firestore: FirebaseFirestore
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            SENDER_TYPE_TEXT_MESSAGE -> {
//                val binding = User1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
//                TextMessageViewHolder(binding)
//            }
//            RECEIVER_TYPE_TEXT_MESSAGE -> {
//                val binding2 = User2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
//                SenderMessageViewHolder(binding2)
//            }
//            else -> throw IllegalArgumentException("Unsupported view type")
//        }
//
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val message = messages[position]
//        when (holder) {
//            is TextMessageViewHolder -> {
//                holder.bind(
//                    message as ChatMessageModel.TextMessageModel
//                )
//
//            }
//            is SenderMessageViewHolder -> {
//                holder.bind2(
//                    message as ChatMessageModel.SenderMessageModel
//                )
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (messages[position]) {
//            is ChatMessageModel.TextMessageModel -> SENDER_TYPE_TEXT_MESSAGE
//            is ChatMessageModel.SenderMessageModel -> RECEIVER_TYPE_TEXT_MESSAGE
//            else -> throw IllegalArgumentException("Unsupported view type")
//        }
//    }
//
//    override fun getItemCount(): Int = messages.size
//
//    fun setMessages(messages: List<ChatMessageModel.TextMessageModel>) {
//        this.messages = messages
//        notifyDataSetChanged()
//    }
//    fun getMessages(messagess: List<ChatMessageModel.SenderMessageModel>) {
//        this.messagess = messagess
//        notifyDataSetChanged()
//    }
//
//    inner class TextMessageViewHolder(private val binding: User1Binding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(message: ChatMessageModel.TextMessageModel) {
//            binding.sender.text = message.message
//            if (message.isSentByCurrentUser) {
//                binding.sender.text = "You"
//            } else {
//                binding.sender.text = message.senderName
//            }
//            binding.timestamp.text = message.timestamp.toString()
//        }
//    }
//
//    inner class SenderMessageViewHolder(private val binding2: User2Binding) :
//        RecyclerView.ViewHolder(binding2.root) {
//        fun bind2(message: ChatMessageModel.SenderMessageModel) {
//            binding2.sender.text = message.message
//            if (message.isSentByCurrentUser) {
//                binding2.sender.text = "Hi"
//            } else {
//                binding2.sender.text = message.senderName
//            }
//            binding2.timestamp.text = message.timestamp.toString()
//        }
//    }
//
//    companion object {
//        private const val SENDER_TYPE_TEXT_MESSAGE = 0
//        private const val RECEIVER_TYPE_TEXT_MESSAGE = 1
//    }
//}


//package com.example.viewpager.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.viewpager.databinding.User1Binding
//import com.example.viewpager.databinding.User2Binding
//import com.example.viewpager.model.ChatMessageModel
//import com.google.firebase.auth.FirebaseUser
//
//class ChatAdapter(private val currentUser: FirebaseUser?) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    private var messages: List<ChatMessageModel> = emptyList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            SENDER_TYPE_TEXT_MESSAGE -> {
//                val binding = User1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
//                TextMessageViewHolder(binding)
//            }
//            RECEIVER_TYPE_TEXT_MESSAGE -> {
//                val binding2 = User2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
//                SenderMessageViewHolder(binding2)
//            }
//            else -> throw IllegalArgumentException("Unsupported view type")
//        }
//
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val message = messages[position]
//        when (holder) {
//            is TextMessageViewHolder -> {
//                holder.bind(
//                    message as ChatMessageModel.TextMessageModel,
//                    currentUser
//                )
//            }
//            is SenderMessageViewHolder -> {
//                holder.bind2(
//                    message as ChatMessageModel.SenderMessageModel,
//                    currentUser
//                )
//            }
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (messages[position]) {
//            is ChatMessageModel.TextMessageModel -> SENDER_TYPE_TEXT_MESSAGE
//            is ChatMessageModel.SenderMessageModel -> RECEIVER_TYPE_TEXT_MESSAGE
//            else -> throw IllegalArgumentException("Unsupported view type")
//        }
//    }
//
//    override fun getItemCount(): Int = messages.size
//
//    fun setMessages(messages: List<ChatMessageModel>) {
//        this.messages = messages
//        notifyDataSetChanged()
//    }
//
//    inner class TextMessageViewHolder(private val binding: User1Binding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(message: ChatMessageModel.TextMessageModel, currentUser: FirebaseUser?) {
//            binding.sender.text = message.message
//            if (message.isSentByCurrentUser) {
//                binding.sender.text = "You"
//            } else {
//                binding.sender.text = message.senderName
//            }
//            binding.timestamp.text = message.timestamp.toString()
//        }
//    }
//    inner class SenderMessageViewHolder(private val binding2: User2Binding):
//        RecyclerView.ViewHolder(binding2.root){
//        fun bind2(message: ChatMessageModel.SenderMessageModel, currentUser: FirebaseUser?) {
//            binding2.receiver.text = message.message
//            if (message.isSentByCurrentUser) {
//                binding2.receiver.text = "Hi"
//            } else {
//                binding2.receiver.text = message.senderName
//            }
//            binding2.timestamp.text = message.timestamp.toString()
//        }
//        }
//
//    companion object {
//        private const val SENDER_TYPE_TEXT_MESSAGE = 0
//        private const val RECEIVER_TYPE_TEXT_MESSAGE = 1
//    }
//}
