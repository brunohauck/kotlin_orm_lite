package com.mentorandroid.loginorm;

import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.mentorandroid.loginorm.dao.BDHelper;
import com.mentorandroid.loginorm.model.Person;

import java.util.ArrayList;
import java.util.List;

public class TradutoraActivity extends AppCompatActivity {

    private Dao<Person, Long> personDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradutora);

        try {
            personDAO = BDHelper.getInstance(this).getPersonDAO();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        Person p = new Person();
        p.setEmail("brunohauck@gmail.com");
        p.setPassword("1234567890");
        List<Person> pResult = new ArrayList<Person>();

        try {
           pResult = personDAO.queryForMatching(p);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        if(pResult.isEmpty()){
            try {
                personDAO.create(p);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Usuário está inserido no banco de dados", Toast.LENGTH_SHORT).show();
        }

        List<Person> pAll = new ArrayList<Person>();
        try {
            pAll = personDAO.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        for (Person pe: pAll) {
            Log.i("Name_>",pe.getName());
            //Toast.makeText(getApplicationContext(), pe.getName().toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
