package com.example.testprojectraduga.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getDocumentList() = apiService.getDocumentList()
}