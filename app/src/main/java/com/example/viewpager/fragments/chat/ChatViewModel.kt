package com.example.viewpager.fragments.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewpager.model.ChatData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.random.Random

class ChatViewModel : ViewModel() {

    private val firestore = Firebase.firestore
    private val chatCollectionRef = firestore.collection("chats").document("1_2").collection("messages")

    private val _chatMessages = MutableLiveData<List<ChatData>>()
    val chatMessages: LiveData<List<ChatData>>
        get() = _chatMessages

    private val _sendResult = MutableLiveData<Boolean>()
    val sendResult: LiveData<Boolean>
        get() = _sendResult

    fun sendTextMessage(messageText: String) {
        val chatMessage = ChatData(
            text = messageText,
            senderId = Random.nextBoolean(),
            senderName = " ",
            timestamp = System.currentTimeMillis(),
        )


        chatCollectionRef.add(chatMessage)
            .addOnSuccessListener { documentReference ->
                _sendResult.postValue(true)
            }
            .addOnFailureListener { e ->
                _sendResult.postValue(false)
            }
    }
    fun observeChatMessages() {
        chatCollectionRef
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val messages = mutableListOf<ChatData>()
                querySnapshot?.documents?.forEach { documentSnapshot ->
                    val message = documentSnapshot.toObject(ChatData::class.java)
                    message?.let { messages.add(it) }
                }
                _chatMessages.postValue(messages)
            }
    }


    //    fun observeChatMessages(chatId: String) {
//        chatCollectionRef.whereEqualTo("senderId", chatId)
//            .orderBy("timestamp", Query.Direction.ASCENDING)
//            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                if (firebaseFirestoreException != null) {
//                    return@addSnapshotListener
//                }
//
//                val chatMessages = mutableListOf<ChatData>()
//                    querySnapshot?.documents?.forEach { document ->
//                        val chatMessage = document.toObject(ChatData::class.java)
//                        if (chatMessage != null) {
//                            val isSentByCurrentUser = chatMessage.senderId
//                            chatMessages.add(
//                                ChatData(
//                                    text = chatMessage.text,
//                                    senderName = chatMessage.senderName,
//                                    timestamp = chatMessage.timestamp,
//                                    senderId = isSentByCurrentUser
//                                )
//                            )
//                        }
//                    }
//
//                _chatMessages.postValue(chatMessages)
//            }
//    }
    private fun generateRandomId(): String {
        val upper = ('A'..'Z').toList().toTypedArray()
        val lower = ('a'..'z').toList().toTypedArray()
        val digits = ('0'..'9').toList().toTypedArray()
        val special = listOf('!', '@', '#', '$', '%', '&', '^', '_', '~').toTypedArray()
        val charPool = upper + lower + digits + special
        val length = 26
        return (1..length)
            .map { i -> Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } // ensure the ID starts with a capital letter
    }
}



//package com.example.viewpager.fragments.chat
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.example.viewpager.model.ChatData
//import com.example.viewpager.model.ChatMessageModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.Query
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase
//
//class ChatViewModel : ViewModel() {
//
//    private val firestore = Firebase.firestore
//    private val chatCollectionRef = firestore.collection("chats")
//
//    private val _chatMessages = MutableLiveData<List<ChatMessageModel.TextMessageModel>>()
//    val chatMessages: LiveData<List<ChatMessageModel.TextMessageModel>>
//        get() = _chatMessages
//    private val _receiverMessages = MutableLiveData<List<ChatMessageModel.SenderMessageModel>>()
//    val receiverMessages: LiveData<List<ChatMessageModel.SenderMessageModel>>
//        get() = _receiverMessages
//
//    private val _sendResult = MutableLiveData<Boolean>()
//    val sendResult: LiveData<Boolean>
//        get() = _sendResult
//
//    fun sendTextMessage(messageText: String) {
//        val currentUser = FirebaseAuth.getInstance().currentUser
//        val chatMessage = ChatData(
//            text = messageText,
//            senderId = currentUser?.uid ?: "",
//            senderName = currentUser?.displayName ?: "",
//            timestamp = System.currentTimeMillis()
//        )
//
//        chatCollectionRef.add(chatMessage)
//            .addOnSuccessListener { documentReference ->
//                _sendResult.postValue(true)
//            }
//            .addOnFailureListener { e ->
//                _sendResult.postValue(false)
//            }
//    }
//
//    fun observeChatMessages(chatId: String) {
//        chatCollectionRef.whereEqualTo("chatId", chatId)
//            .orderBy("timestamp", Query.Direction.ASCENDING)
//            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                if (firebaseFirestoreException != null) {
//                    return@addSnapshotListener
//                }
//
//                val chatMessages = mutableListOf<ChatMessageModel.TextMessageModel>()
//
//                querySnapshot?.documents?.forEach { document ->
//                    val chatMessage = document.toObject(ChatData::class.java)
//                    if (chatMessage != null) {
//                        val isSentByCurrentUser =
//                            chatMessage.senderId == FirebaseAuth.getInstance().currentUser?.uid
//                        chatMessages.add(
//                            ChatMessageModel.TextMessageModel(
//                                message = chatMessage.text,
//                                senderName = chatMessage.senderName,
//                                timestamp = chatMessage.timestamp,
//                                isSentByCurrentUser = isSentByCurrentUser
//                            )
//                        )
//                    }
//                }
//                _chatMessages.postValue(chatMessages)
//
//                val receiverMessages= mutableListOf<ChatMessageModel.SenderMessageModel>()
//                querySnapshot?.documents?.forEach { document ->
//                    val receiverMessage = document.toObject(ChatData::class.java)
//                    if (receiverMessage != null) {
//                        val isSentByCurrentUser =
//                            receiverMessage.senderId == FirebaseAuth.getInstance().currentUser?.uid
//                        receiverMessages.add(
//                            ChatMessageModel.SenderMessageModel(
//                                message = receiverMessage.text,
//                                senderName = receiverMessage.senderName,
//                                timestamp = receiverMessage.timestamp,
//                                isSentByCurrentUser = isSentByCurrentUser
//                            )
//                        )
//                    }
//                }
//                _receiverMessages.postValue(receiverMessages)
//            }
//    }
//}