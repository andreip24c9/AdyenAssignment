package com.adyen.android.assignment.domain.util

import org.junit.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateHelperTest {

    private fun createDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)
    }

    private fun assertDate(
        expected: String,
        actual: String
    ) { Assert.assertEquals(expected, actual) }

    @Test
    fun test_format_short_date() {
        val date = DateHelper.formatShortDate(createDate("2021-05-21"))
        assertDate("21/05/2021", date)
    }

    @Test
    fun test_format_long_date() {
        val date = DateHelper.formatLongDate(createDate("2021-06-24"))
        assertDate("24 June 2021", date)
    }
}