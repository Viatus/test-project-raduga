package com.example.testprojectraduga.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("getdocumentlist")
    suspend fun getDocumentList(): Response<DocumentList>
}