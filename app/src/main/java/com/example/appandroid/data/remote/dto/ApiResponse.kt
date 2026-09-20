package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T,
    val meta: PageMeta? = null,
)

@Serializable
data class PageMeta(
    val page: Int,
    val limit: Int,
    val total: Int,
    val totalPages: Int,
)
