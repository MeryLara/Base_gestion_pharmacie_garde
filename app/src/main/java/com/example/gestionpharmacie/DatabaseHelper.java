package com.example.gestionpharmacie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "GestionPharmacie.db";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;
    //TABLE NAME
    public static final String TABLE_USERS = "user";
    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String id_user = "id";

    //COLUMN user name
    public static final String nom_user = "nom";
    public static final String prenom_user = "prenom";
    //COLUMN email
    public static final String email_user = "email";
    //COLUMN password
    public static final String pass_user = "password";


    public static final String sql_table_user = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + id_user + " INTEGER PRIMARY KEY, "
            + nom_user + " TEXT, "
            + prenom_user + " TEXT, "
            + email_user + " TEXT, "
            + pass_user + " TEXT"
            + " ) ";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

       // db.execSQL(sql_table_user);
        db.execSQL("Create table user(id INTEGER PRIMARY KEY,nom TEXT,prenom TEXT,email TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public boolean insert(String nom,String prenom,String email,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("nom",nom);
        contentValues.put("prenom",prenom);
        contentValues.put("email",email);
        contentValues.put("password",pass);

        long ins=db.insert(TABLE_USERS,null,contentValues);
        if(ins==-1)return  false;
        else  return true;
    }
   public Boolean chkemail (String email){
        SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor=db.rawQuery("select * from user where email=?",new String[]{email});
       if (cursor.getCount()>0)return false;
       else return true;
   }

   public Boolean emailpassword(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from user where email=? and password=?",new String[]{email,password});
        if(cursor.getCount()>0)return true;
        else  return false;
   }

}
