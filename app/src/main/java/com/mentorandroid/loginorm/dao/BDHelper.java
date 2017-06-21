package com.mentorandroid.loginorm.dao;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mentorandroid.loginorm.model.Person;

public class BDHelper extends OrmLiteSqliteOpenHelper{
	private Dao<Person, Long> personDAO = null;
	public static BDHelper instance;
	public static BDHelper getInstance(Context ctx){
		if (instance == null){
			instance = new BDHelper(ctx);
		}
		return instance;
	}
	public BDHelper(Context context) {
		super(context, "TesteORMLite.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		try {
			Log.i(BDHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Person.class);
		} catch (SQLException e) {
			Log.e(BDHelper.class.getName(), "Erro ao criar o BD", e);
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	//otimizar a quantidade de objetos dao criados
	public Dao<Person, Long> getPersonDAO() throws SQLException {

		if (personDAO == null) {
			personDAO = getDao(Person.class);
		}
		return personDAO;
	}
}
