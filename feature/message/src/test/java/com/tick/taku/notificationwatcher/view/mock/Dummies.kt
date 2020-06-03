package com.tick.taku.notificationwatcher.view.mock

import android.graphics.Bitmap
import com.tick.taku.notificationwatcher.domain.db.entity.LatestMessage
import com.tick.taku.notificationwatcher.domain.db.entity.RoomInfoEntity

object Dummies {

    val roomList: List<RoomInfoEntity> = listOf(
        object: RoomInfoEntity {
            override val id: String = "0001"
            override val name: String = "name 0001"
            override val latestMessage: LatestMessage =
                object: LatestMessage {
                    override val message: String = "message 0001"
                    override val date: Long = 0
                    override val icon: Bitmap
                        get() = Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565)
                }
        }
    )

}