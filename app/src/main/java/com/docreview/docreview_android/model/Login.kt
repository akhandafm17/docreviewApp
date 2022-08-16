package com.docreview.docreview_android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login (
    val username: String,
    val password: String,
    val success: Boolean = false,
):Parcelable