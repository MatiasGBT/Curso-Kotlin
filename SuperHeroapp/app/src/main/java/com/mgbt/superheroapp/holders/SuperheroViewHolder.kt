package com.mgbt.superheroapp.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mgbt.superheroapp.databinding.ItemSuperheroBinding
import com.mgbt.superheroapp.entities.SuperheroItemResponse
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSuperheroBinding.bind(view)

    fun bind(superheroItemResponse: SuperheroItemResponse, onItemSelected: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.superheroImage.url).into(binding.ivSuperhero)
        //root es toda la vista, toda la card
        binding.root.setOnClickListener{onItemSelected(superheroItemResponse.idSuperhero)}
    }
}