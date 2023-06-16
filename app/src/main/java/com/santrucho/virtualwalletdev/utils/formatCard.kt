package com.santrucho.virtualwalletdev.utils

fun formatCardNumber(cardNumber: String): String {
    val formattedCardNumber = StringBuilder()
    var index = 0
    while (index < cardNumber.length) {
        if (index > 0 && index % 4 == 0) {
            formattedCardNumber.append(" ")
        }
        formattedCardNumber.append(cardNumber[index])
        index++
    }
    return formattedCardNumber.toString()
}