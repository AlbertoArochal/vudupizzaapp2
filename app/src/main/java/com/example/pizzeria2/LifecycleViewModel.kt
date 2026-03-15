package com.example.pizzeria2

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class LifecycleViewModel : ViewModel() {
    val lifecycleLogs = mutableStateListOf<String>()

    fun addLog(event: String) {
        lifecycleLogs.add(event)
    }

    fun clearLogs() {
        lifecycleLogs.clear()
    }
}

