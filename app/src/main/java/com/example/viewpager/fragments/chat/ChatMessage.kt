package com.example.viewpager.fragments.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager.R
import com.example.viewpager.adapter.ChatAdapter
import com.example.viewpager.databinding.FragmentChatMessageBinding
import com.example.viewpager.model.ChatData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlin.random.Random

class ChatMessage : Fragment() {
    private lateinit var binding: FragmentChatMessageBinding
    private lateinit var Adapter: ChatAdapter
    private lateinit var viewModel: ChatViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatMessageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        binding.charRecycle.layoutManager=linearLayoutManager
        val recyclerView: RecyclerView = binding.charRecycle
        Adapter = ChatAdapter(mutableListOf())
        recyclerView.adapter=Adapter
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        viewModel.chatMessages.observe(viewLifecycleOwner) { messages ->
            Log.d("Chat", "onViewCreated: ${messages.size} ")
            Adapter.setMessages(messages)
//            binding.charRecycle.scrollToPosition(messages.lastIndex)
        }

        firestore = FirebaseFirestore.getInstance()
        val senderID = Random.nextBoolean()
        viewModel.observeChatMessages()

        binding.send.setOnClickListener {
            val messageText = binding.message.text.toString().trim()
            if (messageText.isNotEmpty()) {
                viewModel.sendTextMessage(messageText)
                binding.message.setText("")
                Log.d("msg", messageText)
            }
        }
    }
}


//class ChatMessage : Fragment() {
//    private lateinit var binding:FragmentChatMessageBinding
//    private lateinit var adapter: ChatAdapter
//    private lateinit var viewModel: ChatViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding= FragmentChatMessageBinding.inflate(layoutInflater)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        val chatId = currentUser?.uid ?: ""
//        adapter= ChatAdapter(FirebaseAuth.getInstance().currentUser)
//
//        viewModel= ViewModelProvider(this)[ChatViewModel::class.java]
//        viewModel.chatMessages.observe(viewLifecycleOwner) { messages ->
//            adapter.setMessages(messages)
//            binding.charRecycle.scrollToPosition(messages.lastIndex)
//        }
//        viewModel.receiverMessages.observe(viewLifecycleOwner) { messages ->
//            adapter.setMessages(messages)
//            binding.charRecycle.scrollToPosition(messages.lastIndex)
//        }
//
//        viewModel.observeChatMessages(chatId)
//
//        binding.send.setOnClickListener {
//            val messageText = binding.message.text.toString().trim()
//            if (messageText.isNotEmpty()) {
//                viewModel.sendTextMessage(messageText)
//                binding.message.setText("")
//            }
//        }
//    }
//
//
//}