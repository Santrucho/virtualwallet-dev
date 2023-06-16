package com.santrucho.virtualwalletdev.utils

fun formatMoney(input: String): String {
    val digitsOnly = input.replace(".", "").replace(",", "")
    var formatted = ""
    var remaining = digitsOnly
    val numDigits = digitsOnly.length

    // Add the thousands separators
    if (numDigits >= 4) {
        val lastThousandsSeparatorIndex = numDigits % 3
        if (lastThousandsSeparatorIndex != 0) {
            formatted += remaining.substring(0, lastThousandsSeparatorIndex) + "."
            remaining = remaining.substring(lastThousandsSeparatorIndex)
        }
        for (i in remaining.indices step 3) {
            if (i != 0) {
                formatted += "."
            }
            formatted += remaining.substring(i, i + 3)
        }
    } else {
        formatted = remaining
    }

    return "$$formatted"
}