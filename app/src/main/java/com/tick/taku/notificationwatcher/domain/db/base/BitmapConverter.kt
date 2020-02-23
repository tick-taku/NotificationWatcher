package com.tick.taku.notificationwatcher.domain.db.base

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class BitmapConverter {

    @TypeConverter
    fun Bitmap.toEncodedString(image: Bitmap): String {
        val bos = ByteArrayOutputStream()
        return if (image.compress(Bitmap.CompressFormat.PNG, 50, bos))
            Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)
        else
            ""
    }

    @TypeConverter
    fun String.toBitmap(rawStr: String): Bitmap {
        val raw = Base64.decode(rawStr, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(raw, 0, raw.size)
    }

}