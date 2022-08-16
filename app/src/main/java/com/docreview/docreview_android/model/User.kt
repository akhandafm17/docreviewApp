package com.docreview.docreview_android.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


 data class User (
     val id: Int,
     val email: String,
     val userName: String,
     val firstName: String,
     val lastName: String,
     val passwordHash: String,
     )