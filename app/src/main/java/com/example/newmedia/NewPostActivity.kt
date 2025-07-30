package com.example.newmedia

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newmedia.databinding.ActivityIntentHandlerBinding
import com.example.newmedia.databinding.ActivityNewPostBinding
import com.example.newmedia.util.AndroidUtils

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editText.requestFocus()

        val content = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.editText.setText(content)

        binding.ok.setOnClickListener {
            if(binding.editText.text.isNullOrBlank()) {
                Toast.makeText(this, "Not text", Toast.LENGTH_LONG).show()
                setResult(RESULT_CANCELED)
            } else {
                val result = Intent().putExtra(Intent.EXTRA_TEXT,binding.editText.text.toString())
                setResult(RESULT_OK,result)
            }
            finish()
        }
    }
}