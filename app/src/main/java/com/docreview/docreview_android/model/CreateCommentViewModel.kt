package com.docreview.docreview_android.model

import com.google.gson.annotations.SerializedName

data class CreateCommentViewModel (
     val id: Int,
     val content: String,
     val startIndex: Int,
     val endIndex: Int,
     val upvotes: Int,
     val parent: Boolean,
     val selectedText: String,
     val username: String,
     @SerializedName("docReview")
     val DocReviewId: Int,
     val docreviewname: String
 )