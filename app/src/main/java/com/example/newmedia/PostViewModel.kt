package com.example.newmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newmedia.dto.Post
import com.example.newmedia.repository.PostRepository
import com.example.newmedia.repository.PostRepositoryFileImpl
import com.example.newmedia.repository.PostRepositoryInMemoryImpl
import com.example.newmedia.repository.PostRepositorySharedPrefsImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,

    countLikes = 0,
    countShare = 0,
    countViews = 0,
    repostByMe = false
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val editer = MutableLiveData(empty)

    val data = repository.getAll()
    fun like(id: Int) = repository.like(id)
    fun removeById(id: Int) = repository.removeById(id)

    fun change(post: Post) {
        editer.value = post
    }

    fun changeContentAndSave(text: String) {
        editer.value?.let {
            val trimmed = text.trim()
            if (trimmed == it.content) {
                return
            }
            editer.value = it.copy(content = trimmed)
        }
    }

    fun saveContent() {
        editer.value?.let {
            repository.save(it)
            editer.value = empty
        }
    }
}