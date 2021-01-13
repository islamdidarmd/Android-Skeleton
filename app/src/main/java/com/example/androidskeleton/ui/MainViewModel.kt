package com.example.androidskeleton.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidskeleton.data.model.ApiResponse
import com.example.androidskeleton.data.model.StatefulData
import com.example.androidskeleton.data.model.search.Repo
import com.example.androidskeleton.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    private val TAG = "MainViewModel"

    private val repos = MutableLiveData<StatefulData<ApiResponse<List<Repo>>>>()

    fun insertToDb(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertToDb(query)
        }
    }

    fun getRepoListLiveData(): LiveData<StatefulData<ApiResponse<List<Repo>>>> {
        return repos
    }

    fun searchRepo(query: String): LiveData<StatefulData<ApiResponse<List<Repo>>>> {
        repos.postValue(StatefulData.loading())

        viewModelScope.launch(Dispatchers.Default) {
           val res = repository.searchRepo(query)
            if(res != null)repos.postValue(StatefulData.success(res))
            if(res == null)repos.postValue(StatefulData.error())
        }
        return repos
    }

    fun getRecent(): LiveData<List<String>> {
        return repository.getRecent()
    }
}