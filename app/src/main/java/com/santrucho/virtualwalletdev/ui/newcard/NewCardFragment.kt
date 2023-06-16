package com.santrucho.virtualwalletdev.ui.newcard

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.databinding.FragmentNewCardBinding
import com.santrucho.virtualwalletdev.presentation.CardViewModel
import com.santrucho.virtualwalletdev.presentation.MovementViewModel
import com.santrucho.virtualwalletdev.utils.Resource
import com.santrucho.virtualwalletdev.utils.formatCardNumber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class NewCardFragment() : Fragment() {

    private var _binding : FragmentNewCardBinding? = null
    private val binding get() = _binding!!
    private val viewModel:CardViewModel by viewModels()
    private val movementViewModel: MovementViewModel by viewModels()

    private lateinit var owner : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentNewCardBinding.inflate(inflater,container,false)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        owner = sharedPreferences.getString("username", "").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.GONE

        binding.cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.empty_background)
        setupNumberFieldListener(binding.numberField,binding.numberInCard,binding.cardView)
        setupFieldChangeListener(binding.nameField,binding.nameInCard)
        setupFieldChangeListener(binding.codeField,binding.codeInCard)
        setupFieldChangeListener(binding.expirationField,binding.dateInCard)

        var cardNumber = viewModel.number

        // Configurar el listener del campo de número de tarjeta
        binding.numberField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Actualizar el valor del MutableStateFlow
                cardNumber.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar
            }
        })

        // Observar los cambios en el MutableStateFlow
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            cardNumber.collect { cardNumber ->
                // Aplicar el formato al número de tarjeta
                val formattedCardNumber = formatCardNumber(cardNumber)

                // Actualizar el campo de número de tarjeta con el valor formateado
                binding.numberField.setText(formattedCardNumber)

                // Ajustar la posición del cursor al final
                binding.numberField.setSelection(formattedCardNumber.length)
            }
        }

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
                        generateTransaction()
                        onHomeFragment()
                    }
                    is Resource.Failure -> {
                        // Acciones a realizar cuando se produce un error
                        binding.progressBar.visibility = View.GONE
                        val error = resource.exception.message.toString()
                        Toast.makeText(requireContext(),error,Toast.LENGTH_SHORT).show()
                        // Mostrar mensaje de error o tomar acciones correspondientes
                    }
                    else -> {
                    }
                }
            }
        }

        binding.createCard.setOnClickListener {
            createCard(cardNumber.value,binding.numberField.text.toString(),binding.expirationField.text.toString(),binding.codeField.text.toString())
        }
    }

    private fun createCard(name:String,number:String,expiration:String,code:String){
        if(name.isEmpty() || number.isEmpty() || expiration.isEmpty() || code.isEmpty()){
            Toast.makeText(requireContext(),"Ningun campo puede estar vacio",Toast.LENGTH_SHORT).show()
        } else{
            val cardView = binding.cardView
            val background = updateCardBackground(number,cardView)
            val randomUID = UUID.randomUUID().toString()
            val card = Card(uid = randomUID,name = name,number = number,expiration = expiration,code = code,type = background,owner = owner)
            viewModel.addCard(card)
        }
    }

    private fun generateTransaction(){
        val randomId = (10000000..99999999).random().toString()
        val movement = Movement(id = randomId,"Nueva tarjeta asociada",LocalDate.now().toString(),owner=owner)
        movementViewModel.generateMovement(movement)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility = View.VISIBLE
    }

    private fun onHomeFragment(){
        val action = NewCardFragmentDirections.actionNewCardFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun setupFieldChangeListener(
        field: TextInputEditText,
        textView: TextView,
    ) {
        field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se necesita implementar
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textView.text = s
            }

            override fun afterTextChanged(s: Editable?) {
                // No se necesita implementar
            }
        })
    }

    private fun setupNumberFieldListener(
        field: TextInputEditText,
        textView: TextView,
        cardView: CardView
    ) {
        field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val formattedNumber = formatCardNumber(s.toString())
                textView.text = formattedNumber// Volver a agregar el TextWatcher
                updateCardBackground(s?.toString(), cardView)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun updateCardBackground(cardNumber: String?, cardView: CardView) : Int {
        return if (!cardNumber.isNullOrEmpty() && cardNumber.isNotEmpty()) {
            val cardBackground = when (cardNumber[0].toString()) {
                "4" -> R.drawable.visa
                "5" -> R.drawable.mastercard
                "7" -> R.drawable.amex
                else -> R.drawable.empty_background
            }
            cardView.background = ContextCompat.getDrawable(requireContext(), cardBackground)
            cardBackground
        } else {
            cardView.background = ContextCompat.getDrawable(requireContext(), R.drawable.empty_background)
            R.drawable.empty_background
        }
    }
}