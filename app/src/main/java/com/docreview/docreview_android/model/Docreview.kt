package com.docreview.docreview_android.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Docreview(
    val id: Int,
    val docname: String,
    val comments: Int,
    val project: String,
    val content: String
): Parcelable