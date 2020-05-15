package com.tick.taku.notificationwatcher.domain.db.entity

import android.util.Patterns
import androidx.core.net.toUri
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeTz
import com.tick.taku.android.corecomponent.ktx.isWebUrlSchema

interface MessageEntity {
    val id: String
    val roomId: String
    val userId: String
    val message: String
    val date: Long

    fun localTime(): DateTimeTz = DateTime(date).local

    fun extractWebLink(): List<String> =
        message.split("\n").mapNotNull {
            val p = Patterns.WEB_URL.matcher(it)
            if (p.find()) it.substring(p.start() until p.end()).takeIf { s -> s.toUri().isWebUrlSchema() }
            else null
        }
}