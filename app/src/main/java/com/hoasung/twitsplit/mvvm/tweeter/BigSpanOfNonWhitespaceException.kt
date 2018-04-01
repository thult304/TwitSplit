package com.hoasung.twitsplit.mvvm.tweeter

class BigSpanOfNonWhitespaceException(var invalidWord: String, var maxLength: Int, message: String) : Throwable(message) {

}