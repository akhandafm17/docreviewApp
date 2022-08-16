package com.docreview.docreview_android.screens.viewModel


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.docreview.docreview_android.model.Login

import com.docreview.docreview_android.model.Resource
import com.docreview.docreview_android.model.User
import com.docreview.docreview_android.model.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(private val _repo : Repository): ViewModel() {
    var isLoading = mutableStateOf(false)
    //Users
    private var _getUsers: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    var getUser: LiveData<List<User>> = _getUsers
    //login
    private var _getLogin: MutableLiveData<Login> = MutableLiveData<Login>()
    var getLogin: LiveData<Login> = _getLogin


        @RequiresApi(Build.VERSION_CODES.O)
        suspend fun getProjectManagers(userName: String, password: String): Resource<List<User>> {
            val result = _repo.getProjectManagers()
            if (result is Resource.Success) {
                isLoading.value = true
                _getUsers.value = (result.data?.toMutableList().also {
                    if (it != null) {

                        it.find { it.userName.equals(userName) && it.passwordHash.equals(password) }
                    }
                } as ArrayList<User>?)!!
            }
            return result
        }
    suspend fun login(login: Login): Resource<Login> {
        val result = _repo.login(login)
        if (result is Resource.Success) {
            isLoading.value = true
            _getLogin.value = result.data!!
        }
        return result
    }


}
