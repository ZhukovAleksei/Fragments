package com.example.newmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId = 0

    private var posts = listOf(
        Post(
            id = ++nextId,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 997,
            countViews = 999,
            repostByMe = false

        ), Post(
            id = ++nextId,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет-Привет-Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 999,
            countViews = 999,
            repostByMe = false

        ),
        Post(
            id = ++nextId,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет-Привет-Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 999,
            countViews = 999,
            repostByMe = false

        ),
        Post(
            id = ++nextId,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет-Привет-Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разметке, аналитике и управлению. Мы растем сами и помогаем расти студентам: от новичков до ууверенных профессионалов. Но самое важноеостается с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия - помочь встать на путь роста и начать цепочку перемен -> http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,

            countLikes = 999,
            countShare = 999,
            countViews = 999,
            repostByMe = false

        )
    )


    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun like(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                countLikes = if (it.likedByMe) it.countLikes - 1 else it.countLikes + 1
            )
        }
        data.value = posts
    }

    override fun share(id: Int) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                repostByMe = !it.repostByMe,
                countShare = it.countShare + 1
                //countShare = if (it.repostByMe) it.countShare - 1 else it.countShare + 1
            )
        }
        data.value = posts
    }

    override fun removeById(id: Int) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if(post.id == 0) {
            listOf(post.copy(id = nextId++, author = "Me")) + posts
        } else {
            posts.map { if (it.id != post.id) it else it.copy( content = post.content ) }
        }
        data.value = posts
    }
}