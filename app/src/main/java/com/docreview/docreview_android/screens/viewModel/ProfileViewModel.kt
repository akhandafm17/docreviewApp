package com.docreview.docreview_android.screens.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.docreview.docreview_android.model.Docreview
import com.docreview.docreview_android.model.Resource
import com.docreview.docreview_android.model.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val _repo : Repository): ViewModel() {
    var isLoading = mutableStateOf(false)
    private var _getDocreviews: MutableLiveData<List<Docreview>> = MutableLiveData<List<Docreview>>()
    var getDocreviews: LiveData<List<Docreview>> = _getDocreviews
    private var _getDocreview: MutableLiveData<Docreview> = MutableLiveData<Docreview>()
    var getDocreview: LiveData<Docreview> = _getDocreview

    suspend fun getDocreviews(): Resource<List<Docreview>> {
        val result = _repo.getDocreviews()
        if (result is Resource.Success) {
            isLoading.value = true
            _getDocreviews.value = result.data!!
        }
        return result
    }
    suspend fun getDocreview(id: Int): Resource<Docreview> {
        val result = _repo.getDocreview(id)
        if (result is Resource.Success) {
            isLoading.value = true
            _getDocreview.value = result.data!!
        }
        return result
    }
}