package com.santrucho.virtualwalletdev.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.santrucho.virtualwalletdev.CardAdapter
import com.santrucho.virtualwalletdev.data.FirestoreRepoImpl
import com.santrucho.virtualwalletdev.ui.CardViewModel
import com.santrucho.virtualwalletdev.databinding.FragmentHomeBinding
import com.santrucho.virtualwalletdev.domain.CardUseCaseImpl
import com.santrucho.virtualwalletdev.model.Card
import com.santrucho.virtualwalletdev.utils.Resource

class HomeFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(
            this, CardViewModelFactory(CardUseCaseImpl(FirestoreRepoImpl()))
        ).get(
            CardViewModel::class.java
        )
    }
    private  var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : CardAdapter
    private lateinit var cardList : ArrayList<Card>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()

    }

    private fun observeViewModel(){
        viewModel.fetchCardList.observe(viewLifecycleOwner,Observer{ result ->
            when(result){
                is Resource.Success -> {
                    adapter.setCardList(result.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"An error has ocurred:${result.exception.message}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun initUI(){
        cardList = ArrayList()
        adapter = CardAdapter(cardList)

        val manager = LinearLayoutManager(requireActivity())
        binding.recyclerView.layoutManager = manager
        binding.recyclerView.hasFixedSize()
        binding.recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

    }

}

