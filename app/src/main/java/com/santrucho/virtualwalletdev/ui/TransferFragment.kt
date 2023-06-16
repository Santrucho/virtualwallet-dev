package com.santrucho.virtualwalletdev.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.databinding.FragmentTransferBinding
import com.santrucho.virtualwalletdev.presentation.MovementViewModel
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.utils.Resource
import com.santrucho.virtualwalletdev.utils.formatMoney
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private val movementViewModel:MovementViewModel by viewModels()

    private lateinit var owner:String
    private lateinit var ownerCbu: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentTransferBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        ownerCbu = sharedPreferences.getString("ownerCbu", "").toString()

        owner = sharedPreferences.getString("username", "").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        formatMoney(binding.amountField.text.toString()).toIntOrNull()
        lifecycleScope.launch {
            viewModel.sendMoney.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(),"Transferencia realizada correctamente",Toast.LENGTH_SHORT).show()
                        generateTransaction()
                        navigateToHome()
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        val error = resource.exception.message.toString()
                        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Unit
                    }
                }
            }
        }

        binding.confirmOperationBtn.setOnClickListener {
            transferOperation(
                binding.cbuField.text.toString(),
                ownerCbu,
                binding.amountField.text.toString().toIntOrNull()
            )
        }
        binding.backBtn.setOnClickListener {
            navigateToHome()
        }
    }

    private fun generateTransaction(){
        val randomId = (10000000..99999999).random().toString()
        val movement = Movement(id = randomId,"Transferencia de dinero",
            LocalDate.now().toString(),owner=owner)
        movementViewModel.generateMovement(movement)
    }

    private fun transferOperation(addresseeCbu: String, ownerCbu: String, amount: Int?) {
        viewModel.transferMoney(addresseeCbu, ownerCbu, amount)
    }
    private fun navigateToHome() {
        val action = TransferFragmentDirections.actionTransferFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.VISIBLE
    }
}