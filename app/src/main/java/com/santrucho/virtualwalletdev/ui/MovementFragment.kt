package com.santrucho.virtualwalletdev.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.databinding.FragmentMovementBinding
import com.santrucho.virtualwalletdev.presentation.CardViewModel
import com.santrucho.virtualwalletdev.presentation.MovementViewModel
import com.santrucho.virtualwalletdev.ui.adapter.MovementAdapter
import com.santrucho.virtualwalletdev.utils.CardAdapter
import com.santrucho.virtualwalletdev.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MovementFragment : Fragment() {

    private var _binding : FragmentMovementBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovementViewModel by viewModels()

    private lateinit var adapter : MovementAdapter
    private lateinit var movementList : ArrayList<Movement>

    private lateinit var allMovement : ArrayList<Movement>
    private lateinit var adapterForAll : MovementAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentMovementBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        movementList = ArrayList()
        adapter = MovementAdapter(movementList)

        val manager = LinearLayoutManager(requireActivity())
        setupRecyclerView(binding.recyclerViewToday, adapter, manager)

        getTodayMovement()

        allMovement = ArrayList()
        adapterForAll = MovementAdapter(allMovement)

        val allLayoutManager = LinearLayoutManager(requireActivity())
        setupRecyclerView(binding.recyclerViewAll, adapterForAll, allLayoutManager)

        getAllMovement()

        viewModel.getAllMovement()
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        adapter: MovementAdapter,
        layoutManager: RecyclerView.LayoutManager
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
    }

    private fun getTodayMovement() {
        lifecycleScope.launch {
            viewModel.allMovementState.collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val currentDate = LocalDate.now()
                        val currentMovements = result.data.filter { movement ->
                            val movementDate = LocalDate.parse(movement.date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                            movementDate == currentDate
                        }

                        adapter.setMovementList(currentMovements)
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
                        //binding.noCardsMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun getAllMovement() {
        lifecycleScope.launch {
            viewModel.allMovementState.collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        //binding.noCardsMessage.visibility = View.GONE
                        adapterForAll.setMovementList(result.data)
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
                        //binding.noCardsMessage.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}