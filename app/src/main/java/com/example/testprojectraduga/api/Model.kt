package com.example.testprojectraduga.api



data class DocumentList(
    val exception: DocumentListException,
    val data: List<DocumentListDataItem>
)

data class DocumentListDataItem(
    val id_pos: Long,
    val id_record: Long,
    val nom_route: String,
    val nom_zak: String,
    val id_hd_route: Int?,
    val nom_nakl: String?
)

data class DocumentListException(
    val error: Int,
    val errorMsg: String?
)