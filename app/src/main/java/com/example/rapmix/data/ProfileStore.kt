package com.example.rapmix.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


object ProfileStore {
    var username by mutableStateOf("")
    var nickname by mutableStateOf("")
    var bio by mutableStateOf("")
    var instagram by mutableStateOf("")
    var twitter by mutableStateOf("")


    var musicStyle by mutableStateOf("")
    var skill by mutableStateOf("")
}
