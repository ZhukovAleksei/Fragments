package com.example.newmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newmedia.databinding.FragmentNewPostBinding
import com.example.newmedia.util.StringArg
import com.example.newmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val binding = FragmentNewPostBinding.inflate(inflater,container,false)

        binding.editText.requestFocus()

        arguments?.textArg?.let {
            binding.editText.setText(it)
        }
//        arguments?.textArg?.let(binding.editText::setText)

        binding.ok.setOnClickListener {
            if(binding.editText.text.isNotBlank()){
                val content = binding.editText.text.toString()
                viewModel.changeContentAndSave(content)
                viewModel.saveContent()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}