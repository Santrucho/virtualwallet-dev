package com.santrucho.virtualwalletdev.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.databinding.FragmentDepositBinding
import com.santrucho.virtualwalletdev.presentation.CardViewModel
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.utils.CardAdapter
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DepositFragment : Fragment() {

    private var _binding: FragmentDepositBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CardAdapter
    private lateinit var cardList: ArrayList<Card>

    private lateinit var owner: String

    private val viewModel: UserViewModel by viewModels()
    private val cardViewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDepositBinding.inflate(inflater, container, false)

        val sharedPreferences =
            requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        owner = sharedPreferences.getString("username", "").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.GONE

        initRecyclerView()

        binding.confirmOperationBtn.setOnClickListener {
            depositMoney()
        }
        binding.backBtn.setOnClickListener {
            navigateToHome()
        }

    }

    private fun initRecyclerView() {

        cardViewModel.getAllCards(owner)

        cardList = ArrayList()
        adapter = CardAdapter(cardList)

        val manager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            set3DItem(true)
            setAlpha(true)
        }

        adapter.notifyDataSetChanged()

        lifecycleScope.launch {
            cardViewModel.allCardsState.collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        if (result.data.isNotEmpty()) {
                            binding.progressBar.visibility = View.GONE
                            binding.noCardsMessage.visibility = View.GONE
                            adapter.setCardList(result.data)
                        } else {
                            binding.noCardsMessage.visibility = View.VISIBLE
                        }
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "An error has ocurred:${result.exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        binding.noCardsMessage.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun depositMoney() {

    }

    private fun navigateToHome() {
        val action = DepositFragmentDirections.actionDepositFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).visibility =
            View.VISIBLE
    }
}