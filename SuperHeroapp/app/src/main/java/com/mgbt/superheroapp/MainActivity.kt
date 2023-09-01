package com.mgbt.superheroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgbt.superheroapp.DetailSuperheroActivity.Companion.EXTRA_ID
import com.mgbt.superheroapp.adapters.SuperheroAdapter
import com.mgbt.superheroapp.databinding.ActivityMainBinding
import com.mgbt.superheroapp.entities.SuperheroDataResponse
import com.mgbt.superheroapp.services.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: SuperheroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Se llama cuando el usuario busca
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Se llama cada que el usuario escribe un nuevo carácter, no importa en esta app
                return false
            }
        })

        adapter = SuperheroAdapter{navigateToDetail(it)}
        binding.rvSuperhero.setHasFixedSize(true)
        binding.rvSuperhero.layoutManager = LinearLayoutManager(this)
        binding.rvSuperhero.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true
        //IO es para un hilo secundario (evitar el main). Si la corrutina se tiene que ejecutar en el hilo principal, hay que usar Dispatchers.Main
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse = retrofit.create(SuperheroService::class.java).getSuperheroes(query)
            if (myResponse.isSuccessful) {
                val response: SuperheroDataResponse? = myResponse.body()
                if (response != null && response.response != "error") {
                    //Solo el hilo principal puede modificar las vistas, por lo que para ocultar el ProgressBar hay que usar runOnUiThread
                    runOnUiThread {
                        adapter.updateList(response.superheroes)
                        binding.progressBar.isVisible = false
                    }
                } else {
                    runOnUiThread { binding.progressBar.isVisible = false }
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(idSuperhero: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, idSuperhero)
        startActivity(intent)
    }
}