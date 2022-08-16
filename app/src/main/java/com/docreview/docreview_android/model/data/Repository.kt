
package com.docreview.docreview_android.model.data
import android.util.Log
import com.docreview.docreview_android.model.*
import com.docreview.docreview_android.network.ApiClient
import javax.inject.Inject

class Repository @Inject constructor(private val client: ApiClient) {


    suspend fun getDocreviews(): Resource<List<Docreview>> {
        val response = try {
            client.getDocreviews()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getDocreview(id: Int): Resource<Docreview> {
        val response = try {
            client.getDocreview(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getComments(): Resource<List<Comment>>{
        val response = try {
            client.getComments()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getComment(id: Int): Resource<Comment>{
        val response = try {
            client.getComment(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun deleteComment(id: Int): Resource<Comment>{
        val response = try {
            client.deleteComment(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getCommentsByDocreviewId(id: Int): Resource<List<Comment>> {
        val response = try {
            client.getCommentsByDocreviewId(id)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun addComment(comment: CreateCommentViewModel): Resource<CreateCommentViewModel>{
        val response = try {
            client.addComment(comment)
        } catch (e: Exception) {
            Log.d("NALU","An unknown error occured: ${e.localizedMessage}")
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }
    suspend fun updateComment(id: Int,comment: CreateCommentViewModel): Resource<CreateCommentViewModel>{
        val response = try {
            client.updateComment(id,comment)
        } catch (e: Exception) {
            Log.d("NALU","An unknown error occured: ${e.localizedMessage}")
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }

    suspend fun getProjectManagers(): Resource<List<User>> {
        val response = try {
            client.getProjectManagers()
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }
    suspend fun login(login: Login): Resource<Login> {
        val response = try {
            client.login(login)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured: ${e.localizedMessage}")
        }

        return Resource.Success(response)
    }
}