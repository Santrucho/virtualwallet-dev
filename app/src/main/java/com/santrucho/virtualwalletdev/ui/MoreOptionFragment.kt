package com.santrucho.virtualwalletdev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.santrucho.virtualwalletdev.databinding.FragmentMoreOptionBinding
import com.santrucho.virtualwalletdev.databinding.FragmentNotificationBinding

class MoreOptionFragment : Fragment(){
    private var _binding : FragmentMoreOptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMoreOptionBinding.inflate(inflater,container,false)
        return binding.root
    }
}

