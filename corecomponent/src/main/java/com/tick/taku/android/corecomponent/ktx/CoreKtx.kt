package com.tick.taku.android.corecomponent.ktx

/**
 * Return another value when null for nullability value instead of elvis.
 *
 * @param f return value
 */
inline infix fun <T> T?.guard(f: () -> T): T = this ?: f()