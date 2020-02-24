package com.tick.taku.android.corecomponent.extensions

import android.graphics.Bitmap
import java.nio.ByteBuffer

fun Bitmap.pixelEquals(bitmap: Bitmap): Boolean {
    val self = ByteBuffer.allocate(height * rowBytes).also {
        copyPixelsToBuffer(it)
    }.array() ?: byteArrayOf()
    val target = ByteBuffer.allocate(bitmap.height * bitmap.rowBytes).also {
        bitmap.copyPixelsToBuffer(it)
    }.array() ?: byteArrayOf()

    return self.contentEquals(target)
}