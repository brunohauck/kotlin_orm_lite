package com.mentorandroid.loginorm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.j256.ormlite.dao.Dao
import com.mentorandroid.loginorm.dao.BDHelper
import com.mentorandroid.loginorm.model.Person
import java.sql.SQLException

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        val personDAO: Dao<Person, Long>?
        try {
            personDAO = BDHelper.getInstance(this).getPersonDAO()
        } catch (e: SQLException) {
            throw RuntimeException(e.message, e)
        }


        var passwordEditText: EditText = findViewById(R.id.passwordField) as EditText
        var emailEditText: EditText = findViewById(R.id.emailField) as EditText
        var signUpButton: Button = findViewById(R.id.signupButton) as Button

        signUpButton.setOnClickListener {
            var password = passwordEditText.text.toString()
            var email = emailEditText.text.toString()

            password = password.trim { it <= ' ' }
            email = email.trim { it <= ' ' }

            if (password.isEmpty() || email.isEmpty()) {
                val builder = AlertDialog.Builder(this@CadastroActivity)
                builder.setMessage(R.string.signup_error_message)
                        .setTitle(R.string.signup_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {

                val p = Person()
                p.email = email
                p.password = password
                personDAO.create(p)

            }
        }
    }
}
