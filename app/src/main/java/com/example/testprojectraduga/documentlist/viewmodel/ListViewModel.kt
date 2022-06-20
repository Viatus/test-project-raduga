package com.example.testprojectraduga.documentlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testprojectraduga.api.DocumentListDataItem
import com.example.testprojectraduga.api.RetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: RetrofitRepository) : ViewModel() {

    val data: LiveData<List<DocumentListDataItem>>
        get() = _data

    private val _data = MutableLiveData<List<DocumentListDataItem>>(emptyList())


    private var screenOrientation = -1

    fun setOrientation(newOrientation: Int) {
        if (newOrientation != screenOrientation && screenOrientation != -1) {
            sortData()
        }
        screenOrientation = newOrientation
    }

    init {
        loadData()
    }

    fun copyToBeginning(position: Int) {
        if (position !in 0..(_data.value?.size ?: 0)) {
            return
        }
        _data.value?.let {
            _data.postValue(listOf(it[position]) + it)
        }
    }

    fun copyToEnd(position: Int) {
        if (position !in 0..(_data.value?.size ?: 0)) {
            return
        }
        _data.value?.let {
            _data.postValue(it + listOf(it[position]))
        }
    }

    private fun sortData() {
        _data.value?.let {
            _data.postValue(it.sortedBy { item -> item.nom_zak })
        }
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            val documentList = repository.getDocumentList()
            if (documentList.isSuccessful) {
                documentList.body()?.let {
                    _data.postValue(it.data)
                }
            }
        }
    }
}