package com.example.newmedia.repository

import androidx.lifecycle.LiveData
import com.example.newmedia.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun like(id: Int)
    fun share(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)
}