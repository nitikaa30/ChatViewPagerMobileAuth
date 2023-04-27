package com.example.viewpager.model

import kotlin.random.Random

data class ChatData(
    val text: String = "",
    @field:JvmField
    val senderId: Boolean = true,
    val senderName: String = "",
    val timestamp: Long = System.currentTimeMillis()
){
    constructor() : this("", Random.nextBoolean(), "")
}