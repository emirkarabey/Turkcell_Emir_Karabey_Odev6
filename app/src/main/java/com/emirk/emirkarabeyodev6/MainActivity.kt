package com.emirk.emirkarabeyodev6

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emirk.emirkarabeyodev6.configs.ApiClient
import com.emirk.emirkarabeyodev6.databinding.ActivityMainBinding
import com.emirk.emirkarabeyodev6.model.JWTUser
import com.emirk.emirkarabeyodev6.services.DummyJsonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var dummyService: DummyJsonService
        dummyService = ApiClient.getClient().create(DummyJsonService::class.java)

        binding.loginButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val pass = binding.editTextTextPassword.text.toString()
            val jwtUser = JWTUser(email, pass)
            dummyService.login(jwtUser).enqueue(object : Callback<JWTUser> {
                override fun onResponse(call: Call<JWTUser>, response: Response<JWTUser>) {
                    val jwtUser = response.body()
                    Log.d("status", response.code().toString())
                    if (jwtUser != null) {
                        val intent = Intent(this@MainActivity, ProductsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<JWTUser>, t: Throwable) {
                    Log.e("login", t.toString())
                    Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG)
                        .show()
                }
            })
        }
    }
}
