package com.ahmetborabolat.whatsapp_clone.model

data class MessageModel(
    var message : String? = "",
    var senderId : String? = "",
    var timeStamp : Long? = 0,
)
