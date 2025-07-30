package com.example.newmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newmedia.dto.Post
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

class PostRepositoryFileImpl(private val context: Context) : PostRepository {

    private var nextId = 0
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            data.value = posts
            sync()
        }
    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(FILENAME)
        if (file.exists()) {
            context.openFileInput(FILENAME).bufferedReader().use {
                posts = gson.fromJson(it, type)
                nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                //data.value = posts
            }
        }
    }



override fun getAll(): LiveData<List<Post>> = data

override fun like(id: Int) {
    posts = posts.map {
        if (it.id != id) it else it.copy(
            likedByMe = !it.likedByMe,
            countLikes = if (it.likedByMe) it.countLikes - 1 else it.countLikes + 1
        )
    }
}

override fun share(id: Int) {
    posts = posts.map {
        if (it.id != id) it else it.copy(
            repostByMe = !it.repostByMe,
            countShare = it.countShare + 1
            //countShare = if (it.repostByMe) it.countShare - 1 else it.countShare + 1
        )
    }
}

override fun removeById(id: Int) {
    posts = posts.filter { it.id != id }

}

override fun save(post: Post) {
    posts = if (post.id == 0) {
        listOf(post.copy(id = nextId++, author = "Me")) + posts
    } else {
        posts.map { if (it.id != post.id) it else it.copy(content = post.content) }
    }
}

private fun sync() {
//        val editor = prefs.edit()
//        editor.putString(KEY_POSTS, gson.toJson(posts))
//        editor.apply()
    context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {
        it.write(gson.toJson(posts))
    }
}

companion object {
    private const val FILENAME = "posts.json"         //
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
}
}