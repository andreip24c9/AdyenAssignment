package com.adyen.android.assignment.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateHelper {
    private const val DAY_MONTH_YEAR_SHORT = "dd/MM/YYYY"
    private const val DAY_MONTH_YEAR_LONG = "dd MMMM yyyy"

    private fun formatDate(localDate: LocalDate, format: String) : String {
        val formatters = DateTimeFormatter.ofPattern(format)
        return formatters.format(localDate)
    }

    fun formatShortDate(localDate: LocalDate) : String {
        return formatDate(localDate, DAY_MONTH_YEAR_SHORT)
    }

    fun formatLongDate(localDate: LocalDate) : String {
        return formatDate(localDate, DAY_MONTH_YEAR_LONG)
    }
}