package com.docreview.docreview_android.model

import java.util.*


data class Comment(
    val commentId: Int,
    val user: String,
    val upvotes: Int,
    val docReview: String,
    var content: String,
    //val subcomments: String?,
)