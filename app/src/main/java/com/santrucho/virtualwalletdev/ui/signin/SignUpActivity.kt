package com.santrucho.virtualwalletdev.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.santrucho.virtualwalletdev.MainActivity
import com.santrucho.virtualwalletdev.data.model.User
import com.santrucho.virtualwalletdev.databinding.ActivitySignUpBinding
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding) {
            signUpButton.setOnClickListener {
                createUser(usernameField.text.toString(),emailField.text.toString(),passwordField.text.toString())
            }

            navigateToLoginButton.setOnClickListener {
                navigateToLogin()
            }
        }
    }

    private fun createUser(username: String, email: String, password: String) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
        } else {
            val randomCbu = (1000000000..9999999999).random().toString()
            val user = User(cbu = randomCbu, username, email, password, 0)
            viewModel.createUser(user)

            val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("username", user.username).apply()

            lifecycleScope.launch{
                viewModel.userState.collect{ resource ->
                    when(resource){
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Failure ->{
                            binding.progressBar.visibility = View.GONE
                            val error = resource.exception.message.toString()
                            Toast.makeText(this@SignUpActivity,error,Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Success ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@SignUpActivity,"Usuario creado correctamente",Toast.LENGTH_SHORT).show()
                            navigateToHome()
                        }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}