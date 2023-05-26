package com.santrucho.virtualwalletdev.ui.newcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.databinding.FragmentNewCardBinding
import com.santrucho.virtualwalletdev.presentation.CardViewModel
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class NewCardFragment() : Fragment() {


    private var _binding : FragmentNewCardBinding? = null
    private val binding get() = _binding!!


    private val viewModel:CardViewModel by viewModels()

    /*
    private val viewModel by lazy {
        ViewModelProviders.of(
            this, CardViewModelFactory(CardUseCaseImpl(FirestoreRepoImpl()))
        ).get(
            CardViewModel::class.java
        )
    } */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentNewCardBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE
        lifecycleScope.launch {
            viewModel.cardState.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        // Acciones a realizar cuando se obtiene un resultado exitoso
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Tarjeta anadida correctamente",Toast.LENGTH_SHORT).show()
                        //onHomeFragment()
                    }
                    is Resource.Failure -> {
                        // Acciones a realizar cuando se produce un error
                        binding.progressBar.visibility = View.GONE
                        val error = resource.exception.message.toString()
                        Toast.makeText(requireContext(),error,Toast.LENGTH_SHORT).show()
                        // Mostrar mensaje de error o tomar acciones correspondientes
                    }
                    else -> {}
                }
            }
        }

        binding.createCard.setOnClickListener {
            createCard(binding.nameField.text.toString(),binding.numberField.text.toString(),binding.expirationField.text.toString(),binding.codeField.text.toString())
        }
        binding.backButton.setOnClickListener{
            val action = NewCardFragmentDirections.actionNewCardFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private fun createCard(name:String,number:String,expiration:String,code:String){
        if(name.isEmpty() || number.isEmpty() || expiration.isEmpty() || code.isEmpty()){
            Toast.makeText(requireContext(),"Ningun campo puede estar vacio",Toast.LENGTH_SHORT).show()
        } else{
            val randomUID = UUID.randomUUID().toString()
            val card = Card(uid = randomUID,name = name,number = number,expiration = expiration,code = code)
            viewModel.addCard(card)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }

    /*private fun onHomeFragment(){
        val navController = Navigation.findNavController(requireView())
        val action = NewCardFragmentDirections.actionNewCardFragmentToHomeFragment()
        navController.navigate(action)
    } */
}