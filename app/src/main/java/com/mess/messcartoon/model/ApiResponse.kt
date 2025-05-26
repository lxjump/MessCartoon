package com.mess.messcartoon.model

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val results: T
)