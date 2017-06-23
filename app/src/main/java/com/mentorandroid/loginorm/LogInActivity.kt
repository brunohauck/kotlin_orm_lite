package com.mentorandroid.loginorm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.j256.ormlite.dao.Dao
import com.mentorandroid.loginorm.dao.BDHelper
import com.mentorandroid.loginorm.model.Person
import java.sql.SQLException
import java.util.ArrayList

class LogInActivity : AppCompatActivity() {


    private var mFirebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mFirebaseAuth = FirebaseAuth.getInstance()

        var signUpTextView:TextView = findViewById(R.id.signUpText) as TextView
        var emailEditText:EditText = findViewById(R.id.emailField) as EditText
        var passwordEditText: EditText = findViewById(R.id.passwordField) as EditText
        var logInButton: Button = findViewById(R.id.loginButton) as Button

        val personDAO: Dao<Person, Long>?
        try {
            personDAO = BDHelper.getInstance(this).getPersonDAO()
        } catch (e: SQLException) {
            throw RuntimeException(e.message, e)
        }
        signUpTextView.setOnClickListener {
            val intent = Intent(this@LogInActivity, CadastroActivity::class.java)
            startActivity(intent)
        }
        logInButton.setOnClickListener {
            var email = emailEditText.text.toString()
            var password = passwordEditText.text.toString()

            email = email.trim { it <= ' ' }
            password = password.trim { it <= ' ' }

            if (email.isEmpty() || password.isEmpty()) {
                val builder = AlertDialog.Builder(this@LogInActivity)
                builder.setMessage(R.string.login_error_message)
                        .setTitle(R.string.login_error_title)
                        .setPositiveButton(android.R.string.ok, null)
                val dialog = builder.create()
                dialog.show()
            } else {

                if (Util.isNetworkAvailable(this@LogInActivity)) {

                    mFirebaseAuth!!.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@LogInActivity, OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    val intent = Intent(this@LogInActivity, LogadoActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                } else {
                                    val builder = AlertDialog.Builder(this@LogInActivity)
                                    builder.setMessage(task.exception!!.message)
                                            .setTitle(R.string.login_error_title)
                                            .setPositiveButton(android.R.string.ok, null)
                                    val dialog = builder.create()
                                    dialog.show()
                                }
                            })
                }else{
                    val p = Person()
                    p.email = emailEditText.text.toString()
                    p.password = passwordEditText.text.toString()

                    var pResult: List<Person> = ArrayList()

                    try {
                        pResult = personDAO.queryForMatching(p)
                    } catch (e: java.sql.SQLException) {
                        e.printStackTrace()
                    }

                    if (pResult.isEmpty()) {
                        Toast.makeText(applicationContext, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Login funcionou com sucesso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LogadoActivity::class.java)
                        startActivity(intent)

                    }
                }
            }

        }

    }
}
