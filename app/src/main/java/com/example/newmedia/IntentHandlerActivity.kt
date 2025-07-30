package com.example.newmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newmedia.databinding.ActivityIntentHandlerBinding
import com.google.android.material.snackbar.Snackbar

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if(it.action != Intent.ACTION_SEND){
                return@let
            }
            val text = intent.getStringExtra(Intent.EXTRA_TEXT)

            if(text.isNullOrBlank()){
                Snackbar.make(binding.root, "пустой или ноль!", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        finish()
                    }.show()
            }
        }
    }
}