
package com.example.healthyman.Util

class DayPartFormatter(private val partsOfDay: List<String>) : com.github.mikephil.charting.formatter.IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return partsOfDay.getOrNull(value.toInt()) ?: value.toString()
    }
}