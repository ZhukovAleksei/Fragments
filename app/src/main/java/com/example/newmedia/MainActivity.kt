package com.example.newmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        binding.group.visibility = View.GONE

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }
            override fun onRemove(post: Post) {
                    viewModel.removeById(post.id)
            }
            override fun onChange(post: Post) {
                viewModel.change(post)
                binding.group.visibility = View.VISIBLE
                binding.editContent.text = post.content
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

        viewModel.editer.observe(this) {
            if(it.id != 0){
                binding.editText.setText(it.content)
                binding.editText.focusAndShowKeyboard()
            }
        }

        binding.add.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, "Not text", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.changeContentAndSave(text)
            binding.editText.setText("")
            binding.editText.clearFocus()
            AndroidUtils.hideKeyBoard(it)
            binding.group.visibility = View.GONE
        }

        binding.cancel.setOnClickListener {
            with(binding.editContent) {
                text = ""
                clearFocus()
                AndroidUtils.hideKeyBoard(this)
                binding.group.visibility = View.GONE
                viewModel.saveContent()
            }
            binding.editText.setText("")
        }
    }
}
