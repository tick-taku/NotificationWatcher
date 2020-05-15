package com.tick.taku.android.corecomponent.ktx

import android.net.Uri

fun Uri.isWebUrlSchema(): Boolean = scheme?.let { (it == "http") or (it == "https") } ?: false