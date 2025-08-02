package com.example.newmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newmedia.activity.NewPostFragment.Companion.textArg
import com.example.newmedia.R
import com.example.newmedia.adapter.OnInteractionListener
import com.example.newmedia.adapter.PostsAdapter
import com.example.newmedia.databinding.FragmentFeedBinding
import com.example.newmedia.dto.Post
import com.example.newmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val binding = FragmentFeedBinding.inflate(inflater,container,false)

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
            override fun onEdit(post: Post) {
                viewModel.change(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment2,
                    Bundle().apply { textArg = post.content }
                )
            }
        }
        )

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.scrollToPosition(0)
                }
            }
        }
        viewModel.editer.observe(viewLifecycleOwner) { posts ->

        }
        binding.fab.setOnClickListener{
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment2)
        }

        return binding.root
    }
}

