package com.mentorandroid.loginorm

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class ListaTodoActivity : AppCompatActivity() {


    private var mFirebaseAuth: FirebaseAuth? = null
    private var mFirebaseUser: FirebaseUser? = null
    private var mDatabase: DatabaseReference? = null
    private var mUserId: String? = null
    private var ctx: Context? = null
    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_todo)

        mFirebaseAuth = FirebaseAuth.getInstance()
        mFirebaseUser = mFirebaseAuth!!.currentUser
        rootView = findViewById(android.R.id.content)
        ctx = this

        // Initialize Firebase Auth and Database Reference
//        mFirebaseAuth = FirebaseAuth.getInstance()
//        mFirebaseUser = mFirebaseAuth!!.currentUser
//        mDatabase = FirebaseDatabase.getInstance().reference

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView()
        } else {
            WorkAround.functionSetAdapter(ctx, rootView);


        }
    }
    private fun loadLogInView() {
        val intent = Intent(this, LogInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
