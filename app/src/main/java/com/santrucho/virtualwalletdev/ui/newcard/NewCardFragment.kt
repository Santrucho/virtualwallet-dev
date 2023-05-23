package com.santrucho.virtualwalletdev.ui.newcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.santrucho.virtualwalletdev.databinding.FragmentNewCardBinding

class NewCardFragment : Fragment() {

    private var _binding : FragmentNewCardBinding? = null
    private val binding get() = _binding!!
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

        binding.createCard.setOnClickListener {
            createCard(binding.nameField.text.toString(),binding.numberField.text.toString(),binding.expirationField.text.toString(),binding.codeField.text.toString())
        }
        binding.backButton.setOnClickListener{
            onHomeFragment()
        }
    }

    private fun createCard(name:String,number:String,expiration:String,code:String){
        if(name.isNullOrEmpty() || number.isNullOrEmpty() || expiration.isNullOrEmpty() || code.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Ningun campo puede estar vacio",Toast.LENGTH_SHORT).show()
        } else{
            //viewModel.setCard(name,number,expiration,code)
        }
    }

    private fun onHomeFragment(){
        val action = NewCardFragmentDirections.actionNewCardFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}