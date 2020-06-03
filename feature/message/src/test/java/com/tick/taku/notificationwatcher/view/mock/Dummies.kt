package com.tick.taku.notificationwatcher.view.mock

import android.graphics.Bitmap
import com.tick.taku.notificationwatcher.domain.db.entity.*

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

    val messageList: List<UserMessageEntity> = listOf(
        object: UserMessageEntity {
            override val user = object: UserEntity {
                override val id: String = "0001"
                override val name: String = "name"
                override val icon: Bitmap
                    get() = Bitmap.createBitmap(0, 0, Bitmap.Config.RGB_565)
            }
            override val message = object: MessageEntity {
                override val id: String = "0001"
                override val roomId: String = "000001"
                override val userId: String = "000001"
                override val message: String = "message"
                override val date: Long = 0
                override val imageUrl: String = "image_url"
            }
        }
    )

}