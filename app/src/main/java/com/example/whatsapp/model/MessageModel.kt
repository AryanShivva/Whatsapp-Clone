package com.example.whatsapp.model

import java.sql.Timestamp
import java.util.TimeZone

data class MessageModel(
    var message : String? = "",
    var senderId : String? = "",
    var timestamp: Long? = 0


)
