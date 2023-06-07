package com.santrucho.virtualwalletdev.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.santrucho.virtualwalletdev.MainActivity
import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.databinding.ActivityLoginBinding
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.loginState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        val error = resource.exception.message.toString()
                        Toast.makeText(this@LoginActivity, error, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }
                    else -> {
                    }
                }
            }
        }

        with(binding){
            loginButton.setOnClickListener {
                login(usernameField.text.toString(),passwordField.text.toString())
            }
        }

        binding.navigateToSignupButton.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun login(username:String,password:String){
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(username,password)
            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("username", username).apply()
        }
    }

    private fun navigateToSignUp(){
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHome(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}