package com.docreview.docreview_android.network

import com.docreview.docreview_android.model.*
import com.docreview.docreview_android.screens.viewModel.CommentViewModel
import retrofit2.http.*

interface ApiClient {

    @GET("api/Comment/{id}")
    suspend fun getComment(@Path("id") id: Int): Comment

    //@DELETE("api/Comment/getComments/{id}")
    @DELETE("api/docreview/Comment/getComments/{id}")
    suspend fun deleteComment(@Path("id") id: Int): Comment

    //@POST("api/Comment/AddComment")
    @POST("api/docreview/Comment/AddComment")
    suspend fun addComment(@Body comment: CreateCommentViewModel): CreateCommentViewModel

    //@PATCH("api/Comment/updateComment/{id}")
    @PATCH("api/docreview/Comment/updateComment/{id}")
    suspend fun updateComment(@Path("id") id: Int, @Body comment: CreateCommentViewModel): CreateCommentViewModel

    @GET("api/Comment")
    suspend fun getComments(): List<Comment>

    @GET("api/docreview/docreview/{id}")
    suspend fun getDocreview(@Path("id") id: Int): Docreview

    @GET("api/docreview/docreview")
    suspend fun getDocreviews(): List<Docreview>

    @GET("api/User/Projectmanager")
    suspend fun getProjectManagers(): List<User>

    @GET("api/User/user/{id}")
    suspend fun getUser(@Path("id") id: Int): User

    //@GET("api/Comment/getComments/{id}")
    @GET("api/docreview/Comment/getComments/{id}")
    suspend fun getCommentsByDocreviewId(@Path("id") id: Int): List<Comment>

    @POST("Account/LoginAndroid")
    suspend fun login(@Body login: Login): Login


}