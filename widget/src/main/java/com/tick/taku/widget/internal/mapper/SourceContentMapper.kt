package com.tick.taku.widget.internal.mapper

import com.tick.taku.android.corecomponent.ktx.guard
import com.tick.taku.widget.internal.SourceContent
import org.jsoup.nodes.Document
import java.util.*

internal fun Document.toSourceContent() = SourceContent(metaTitle, description, imageUrl)

private val Document.metaTitle: String
    get() = select("meta[property=og:title]").attr("content").takeIf { it.isNotEmpty() } ?: title()

private val Document.description: String
    get() {
        val d = select("meta").find {
            it.attr("property") == "og:description" || it.attr("name").toLowerCase(Locale.ROOT) == "description"
        }?.attr("content") ?: ""
        return d.trim().replace("\n", " ")
    }

private val Document.imageUrl: String
    get() =
        select("meta[property=og:image]").attr("content").takeIf { it.isNotEmpty() } guard {
            select("link").find {
                when (it.attr("rel").toLowerCase(Locale.ROOT)) {
                    "image_src", "apple-touch-icon", "icon" -> true
                    else -> false
                }
            }?.attr("href") ?: ""
        }