package com.example.nadiri

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Information(
    val name: String = "",
    val mobile: String? = "",
    val city: String = ""
)