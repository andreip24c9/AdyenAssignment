package com.adyen.android.assignment.network.util

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return "No network connection"
    }
}