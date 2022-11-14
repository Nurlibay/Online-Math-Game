package uz.nurlibaydev.onlinemathgame.utils.extenions

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import java.text.SimpleDateFormat
import java.util.*

fun String.coloredString(query: String?): Spannable {
    val spannable = SpannableStringBuilder(this)
    if (query == null) return spannable
    if (!this.contains(query)) return spannable
    if (query == this) {
        spannable.setSpan(
            ForegroundColorSpan(Color.parseColor("#017EF1")),
            0, this.length,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        return spannable
    }
    val start = this.indexOf(query)
    val end = query.length + start
    spannable.setSpan(
        ForegroundColorSpan(Color.parseColor("#017EF1")),
        start, end,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
    return spannable
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val c = Calendar.getInstance().time
    return SimpleDateFormat("MMM dd,yyyy").format(c).uppercase()
}