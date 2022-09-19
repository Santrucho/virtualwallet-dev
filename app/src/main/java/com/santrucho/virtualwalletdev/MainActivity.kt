package com.santrucho.virtualwalletdev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.santrucho.virtualwalletdev.data.FirestoreRepoImpl
import com.santrucho.virtualwalletdev.databinding.ActivityMainBinding
import com.santrucho.virtualwalletdev.domain.CardUseCaseImpl
import com.santrucho.virtualwalletdev.ui.CardViewModel
import com.santrucho.virtualwalletdev.ui.CardViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}