package com.swipechef.app.data.models

data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis()
)