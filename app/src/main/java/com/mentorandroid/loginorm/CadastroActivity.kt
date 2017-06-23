package com.mentorandroid.loginorm

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.j256.ormlite.dao.Dao
import com.mentorandroid.loginorm.dao.BDHelper
import com.mentorandroid.loginorm.model.Person
import java.sql.SQLException

class CadastroActivity : AppCompatActivity() {

    private var mFirebaseAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        mFirebaseAuth = FirebaseAuth.getInstance()
        val personDAO: Dao<Person, Long>?
        try {
            personDAO = BDHelper.getInstance(this).getPersonDAO()
        } catch (e: SQLException) {
            throw RuntimeException(e.message, e)
        }
        //personDAO.queryForAll();

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
                //Verifica se tem conexao com internet
                if (Util.isNetworkAvailable(this@CadastroActivity)) {
                    mFirebaseAuth!!.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this@CadastroActivity, OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    //Crio o usuário também no SQLite para fazer o sincronismo
                                    val p = Person()
                                    p.email = email
                                    p.password = password
                                    personDAO.create(p)
                                    val intent = Intent(this@CadastroActivity, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                } else {
                                    val builder = AlertDialog.Builder(this@CadastroActivity)
                                    builder.setMessage(task.exception!!.message)
                                            .setTitle(R.string.login_error_title)
                                            .setPositiveButton(android.R.string.ok, null)
                                    val dialog = builder.create()
                                    dialog.show()
                                }
                            })

                }
            }
        }
    }
}
