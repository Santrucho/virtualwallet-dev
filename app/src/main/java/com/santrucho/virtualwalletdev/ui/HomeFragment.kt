package com.santrucho.virtualwalletdev.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.databinding.FragmentHomeBinding
import com.santrucho.virtualwalletdev.presentation.CardViewModel
import com.santrucho.virtualwalletdev.presentation.UserViewModel
import com.santrucho.virtualwalletdev.utils.CardAdapter
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var adapter: CardAdapter
    private lateinit var cardList: ArrayList<Card>

    private lateinit var username : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        username = sharedPreferences.getString("username", "").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllCards(username)

        initUI()

        binding.addCardButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewCardFragment()
            findNavController().navigate(action)
        }
    }

    private fun initUI() {


        userViewModel.getUserData(username)
        lifecycleScope.launch {
            userViewModel.userInfo.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.username.text = resource.data?.username
                        binding.balance.text = resource.data?.balance.toString()
                    }
                    is Resource.Failure -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }

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

        getAllCards()

    }

    private fun getAllCards() {
        lifecycleScope.launch {
            viewModel.allCardsState.collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.noCardsMessage.visibility = View.GONE
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
                        binding.noCardsMessage.visibility = View.GONE
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
}

