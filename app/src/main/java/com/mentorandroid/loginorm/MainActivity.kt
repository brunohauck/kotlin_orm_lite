package com.mentorandroid.loginorm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttonCadastro: Button = findViewById(R.id.buttonCadastro) as Button

        buttonCadastro.setOnClickListener{
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)

        }
    }

    fun loginOnClick(view: View){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)

    }

    fun testaBancoOnClick(view: View){
        val intent = Intent(this, TradutoraActivity::class.java)
        startActivity(intent)

    }
}
