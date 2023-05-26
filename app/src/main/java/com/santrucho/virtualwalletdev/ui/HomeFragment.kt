package com.santrucho.virtualwalletdev.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.databinding.FragmentHomeBinding
import com.santrucho.virtualwalletdev.utils.CardAdapter


class HomeFragment : Fragment() {
    /*
    private val viewModel by lazy {
        ViewModelProviders.of(
            this, CardViewModelFactory(CardUseCaseImpl(FirestoreRepoImpl()))
        ).get(
            CardViewModel::class.java
        )
    } */

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

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        //getListOfCards()
        binding.addCardButton.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToNewCardFragment()
            findNavController().navigate(action)
        }
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
    /*
    private fun getListOfCards(){
        viewModel.fetchCardList.observe(viewLifecycleOwner,Observer{ result ->
            when(result){
                is Resource.Success -> {
                    adapter.setCardList(result.data)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"An error has ocurred:${result.exception.message}",Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(),"Another error",Toast.LENGTH_SHORT).show()
                }
            }
        })
    } */

    /*
    private fun deleteAllCards(listCard : MutableList<Card>){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete All Cards")
        builder.setMessage("You sure want delete all cards?")
        builder.setPositiveButton("YES") { _, _ ->
            viewModel.deleteAll(listCard)
            Toast.makeText(context,"Cards remove with success",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("NO"){ _ , _ ->
            Toast.makeText(context,"Remove Cancelled",Toast.LENGTH_SHORT).show()
        }
        builder.show()
    } */


}

