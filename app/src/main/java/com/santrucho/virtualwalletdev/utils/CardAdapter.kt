package com.santrucho.virtualwalletdev

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.santrucho.virtualwalletdev.databinding.ItemCardBinding
import com.santrucho.virtualwalletdev.model.Card

class CardAdapter(private var listCard : List<Card>) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card,parent,false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = listCard[position]
        holder.bind(card)
    }

    override fun getItemCount(): Int = listCard.size

    fun setCardList(cardList : List<Card>){
        this.listCard = cardList
        notifyDataSetChanged()
    }

}

class CardViewHolder(view:View) : RecyclerView.ViewHolder(view){

    private val binding = ItemCardBinding.bind(view)

    fun bind(card: Card){
        binding.nameCard.text = card.name
        binding.numberCard.text = card.number
        binding.expirationCard.text = card.expiration
        binding.codeCard.text = card.code
    }
}