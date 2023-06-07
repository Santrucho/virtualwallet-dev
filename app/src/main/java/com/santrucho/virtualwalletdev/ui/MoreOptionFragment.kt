package com.santrucho.virtualwalletdev.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.santrucho.virtualwalletdev.databinding.FragmentMoreOptionBinding
import com.santrucho.virtualwalletdev.databinding.FragmentNotificationBinding
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.ui.signin.LoginActivity
import com.santrucho.virtualwalletdev.ui.signin.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreOptionFragment : Fragment(){
    private var _binding : FragmentMoreOptionBinding? = null
    private val binding get() = _binding!!

    private val viewModel : UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMoreOptionBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signOut.setOnClickListener {
            viewModel.signOut()
            viewModelStore.clear()
            navigateToLogin()
        }
    }
    private fun navigateToLogin(){
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
    }
}

