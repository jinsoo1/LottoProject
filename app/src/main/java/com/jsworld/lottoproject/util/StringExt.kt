package com.jsworld.lottoproject.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.toSHA1(): String {
    val hexChars = "0123456789abcdef"
    val bytes = MessageDigest
        .getInstance("SHA-1")
        .digest(this.toByteArray())
    val result = StringBuilder(bytes.size * 2)

    bytes.forEach {
        val i = it.toInt()
        result.append(hexChars[i shr 4 and 0x0f])
        result.append(hexChars[i and 0x0f])
    }

    return result.toString()
}

fun String.toRequestBody() = RequestBody.create("plain/text".toMediaTypeOrNull(), this)

object StringExt {
    private val shortDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    private val longDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.KOREA)


    fun showDateAsShort(origin: String): String {
        return shortDateFormat.format(longDateFormat.parse(origin) ?: return "")
    }
    fun formattedStringPrice(origin: Long): String {
        val myFormatter = DecimalFormat("###,###")
        return myFormatter.format(origin)
    }
}
