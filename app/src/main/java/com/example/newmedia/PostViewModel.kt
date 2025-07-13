package com.example.newmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newmedia.dto.Post
import com.example.newmedia.repository.PostRepository
import com.example.newmedia.repository.PostRepositoryInMemoryImpl

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

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val editer = MutableLiveData(empty)

    val data = repository.getAll()
    fun like(id: Int) = repository.like(id)
    fun share(id: Int) = repository.share(id)
    fun removeById(id: Int) = repository.removeById(id)

    fun change(post: Post) {
        editer.value = post
    }

    fun changeContentAndSave(text: String) {
        editer.value?.let{
            if(it.content != text){
                repository.save(it.copy(content = text))
            }
        }
        editer.value = empty
    }

    fun saveContent() {
        editer.value?.let {
            repository.save(it)
            editer.value = empty
        }
    }
}