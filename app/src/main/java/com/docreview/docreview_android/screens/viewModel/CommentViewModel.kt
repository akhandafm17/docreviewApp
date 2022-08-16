package com.docreview.docreview_android.screens.viewModel

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.docreview.docreview_android.model.*
import com.docreview.docreview_android.model.data.Repository
import com.docreview.docreview_android.screens.comments.docreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val _repo : Repository): ViewModel() {
    var isLoading = mutableStateOf(false)
    //Comments
    private var _getComments: MutableLiveData<ArrayList<Comment>> = MutableLiveData<ArrayList<Comment>>()
    var getComments: MutableLiveData<ArrayList<Comment>> = _getComments
    //Users
    private var _getUsers: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()
    var getUser: MutableLiveData<ArrayList<User>> = _getUsers

    suspend fun getComments(id: Int): Resource<List<Comment>> {
        val result = _repo.getCommentsByDocreviewId(id)
        if (result is Resource.Success) {
            isLoading.value = true
            _getComments.value = (result.data as ArrayList<Comment>?)!!
        }
        return result
    }

    suspend fun deleteComment(id: Int) {
        _repo.deleteComment(id)
        isLoading.value = true
        val c = _getComments.value?.find { x -> x.commentId.equals(id) }
        _getComments.value =  (_getComments.value?.toMutableList().also {
            if (it != null) {
                it.remove(c)
            }
        } as ArrayList<Comment>?)!!

    }

    suspend fun addComment(comment: CreateCommentViewModel){
        _repo.addComment(comment)
        isLoading.value = true
        var com = Comment(
            commentId = comment.id,
            user = comment.username,
            upvotes = comment.upvotes,
            docReview = comment.docreviewname,
            content = comment.content,
            //subcomments = null
        )
        _getComments.value = (_getComments.value?.toMutableList().also {
            if (it != null) {
                it.add(com)
            }
        } as ArrayList<Comment>?)!!
    }

    suspend fun updateComment(id: Int,comment: CreateCommentViewModel){
        _repo.updateComment(id, comment)
        isLoading.value = true
        _getComments.value = (_getComments.value?.toMutableList().also {
            if (it != null) {
                it.find { it.commentId == id }?.content = comment.content
            }
        } as ArrayList<Comment>?)!!
    }

    suspend fun getProjectManagers(userName: String, password: String): Resource<List<User>> {
        val result = _repo.getProjectManagers()
        if (result is Resource.Success) {
            isLoading.value = true
            _getUsers.value = (_getUsers.value?.toMutableList().also {
                if (it != null) {
                    it.find { it.userName.equals(userName) && it.passwordHash.equals(password) }
                }
            } as ArrayList<User>?)!!
        }
        return result
    }

}