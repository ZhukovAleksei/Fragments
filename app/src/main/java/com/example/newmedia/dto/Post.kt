package com.example.newmedia.dto

data class Post (
    val id: Int,
    val author: String,
    val published: String,
    val content: String,
    val likedByMe: Boolean,

    val countLikes:Long,
    val countShare:Long,
    val countViews:Long,
    val repostByMe:Boolean
    )