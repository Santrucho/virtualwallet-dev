package com.santrucho.virtualwalletdev.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.santrucho.virtualwalletdev.R
import com.santrucho.virtualwalletdev.data.model.Card
import com.santrucho.virtualwalletdev.data.model.Movement
import com.santrucho.virtualwalletdev.databinding.ItemCardBinding
import com.santrucho.virtualwalletdev.databinding.ItemMovementBinding

class MovementAdapter(private var movementList : List<Movement>) : RecyclerView.Adapter<MovementViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovementViewHolder(layoutInflater.inflate(R.layout.item_movement,parent,false))
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        val movement = movementList[position]
        holder.bind(movement)
    }

    override fun getItemCount(): Int = movementList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setMovementList(listOfTransactions : List<Movement>){
        this.movementList = listOfTransactions
        notifyDataSetChanged()
    }

}

class MovementViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val binding = ItemMovementBinding.bind(view)

    fun bind(movement:Movement){
        binding.operationDate.text = movement.date
        binding.operation.text = movement.operation
        binding.movementId.text = movement.id
    }
}