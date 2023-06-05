package com.emirk.emirkarabeyodev6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.emirk.emirkarabeyodev6.adapter.ProductAdapter
import com.emirk.emirkarabeyodev6.configs.ApiClient
import com.emirk.emirkarabeyodev6.databinding.ActivityProductsBinding
import com.emirk.emirkarabeyodev6.model.Products
import com.emirk.emirkarabeyodev6.services.DummyJsonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyService: DummyJsonService = ApiClient.getClient().create(DummyJsonService::class.java)

        dummyService.getProducts(10).enqueue(object : Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    val adapter = response.body()?.let { ProductAdapter(it) }
                    binding.listView.adapter = adapter
                } else {
                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.v("data","fail")
            }
        })


        binding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    dummyService.searchProducts(query = query).enqueue(object : Callback<Products> {
                        override fun onResponse(call: Call<Products>, response: Response<Products>) {
                            if (response.isSuccessful) {
                                val adapter = ProductAdapter(response.body()!!)
                                binding.listView.adapter = adapter
                            } else {
                                Log.v("data","gelmedi")
                            }
                        }

                        override fun onFailure(call: Call<Products>, t: Throwable) {
                            Log.v("data","fail")
                        }
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@ProductsActivity,DetailActivity::class.java)
            val product = parent.getItemAtPosition(position) as Products
            intent.putExtra("title", product.products[position].title)
            intent.putExtra("price", product.products[position].price)
            intent.putExtra("image", product.products[position].image)
            startActivity(intent)
        }
    }
}