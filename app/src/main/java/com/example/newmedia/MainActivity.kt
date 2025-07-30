package com.example.newmedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newmedia.adapter.OnInteractionListener
import com.example.newmedia.adapter.PostsAdapter
import com.example.newmedia.databinding.ActivityMainBinding
import com.example.newmedia.dto.Post
import com.example.newmedia.util.AndroidUtils
import com.example.newmedia.util.AndroidUtils.focusAndShowKeyboard

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newPostLauncher = registerForActivityResult(NewPostResultContract) { content ->
            content?:return@registerForActivityResult
            viewModel.changeContentAndSave(content)
            viewModel.saveContent()
        }

        val editPostLauncher = registerForActivityResult(EditPostActivityContract){ content ->
            content?: return@registerForActivityResult
            viewModel.changeContentAndSave(content)
            viewModel.saveContent()
        }


        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }
            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val sharedIntent = Intent.createChooser(intent,  getString(R.string.chooser_share_post))
                startActivity(sharedIntent)
            }
            override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
            }
            override fun onChange(post: Post) {
                viewModel.change(post)
                editPostLauncher.launch(post.content)
            }
        }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.scrollToPosition(0)
                }
            }
        }
        binding.fab.setOnClickListener{
            newPostLauncher.launch()
        }
    }
}

