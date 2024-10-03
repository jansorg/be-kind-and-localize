package dev.ja.localize

import com.intellij.DynamicBundle
import com.intellij.openapi.util.text.StringUtilRt
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.pow

object LocalizedStringUtil {
    /**
     * [StringUtilRt.formatFileSize] fixed to use the IDE's selected locale.
     */
    fun formatFileSize(fileSize: Long, unitSeparator: String, rank: Int, fixedFractionPrecision: Boolean): String {
        var rank = rank
        if (fileSize == 0L) return '0'.toString() + unitSeparator + 'B'
        if (rank < 0) {
            rank = StringUtilRt.rankForFileSize(fileSize)
        }

        val value = fileSize / 1000.0.pow(rank.toDouble())
        val units = arrayOf("B", "kB", "MB", "GB", "TB", "PB", "EB")
        val decimalFormat = DecimalFormat("0.##", DecimalFormatSymbols.getInstance(DynamicBundle.getLocale()))
        if (fixedFractionPrecision) {
            decimalFormat.minimumFractionDigits = 2
        }
        return decimalFormat.format(value) + unitSeparator + units[rank]
    }
}