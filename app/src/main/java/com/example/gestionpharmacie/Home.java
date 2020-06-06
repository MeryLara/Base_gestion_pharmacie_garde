package com.example.gestionpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.gestionpharmacie.map.GoogleMapActivity;
import com.example.gestionpharmacie.metier.Adapter;

import com.example.gestionpharmacie.metier.Garde;
import com.example.gestionpharmacie.metier.Pharmacie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import static java.lang.String.valueOf;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private  RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;


    RecyclerView.LayoutManager layoutManager;
    List<Garde> gardes;

    //-----------------pop up

    FloatingActionButton addGarde ;

    NavigationView navigationView;

    RequestQueue rq;
    String req_url="http://192.168.1.162/android/pharmacie/listpharmacie.php";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Pharmacies Garde");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences ("type_user_prefs",MODE_PRIVATE);
        String userType = prefs.getString("userType","user");
        Boolean login=prefs.getBoolean("login",false);

        if(!login){
            Intent add=new Intent(getApplicationContext(),Login.class);
            startActivity(add);

        }
        if(userType.equals("user")) {
            hideItemsForUser();
        }





        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




//-------------------------------------------------------------------------------

       drawerLayout=(DrawerLayout) findViewById(R.id.draw);

        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


//-----------------------------------------------------------------------------------
        rq= Volley.newRequestQueue(this);
        recyclerView=(RecyclerView)findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
      //  pharmacies=new ArrayList<>();
        gardes=new ArrayList<>();
        sendRequest();

//--------------------------------------------- pop up-------------------------------------
       addGarde=(FloatingActionButton)findViewById(R.id.btnAjoutGarde);
       addGarde.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent add=new Intent(getApplicationContext(),AddGarde.class);
               startActivity(add);
           }
       });





    }

    private void hideItemsForUser()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.ajouPhar).setVisible(false);
        nav_Menu.findItem(R.id.notification).setVisible(false);
    }

    private void sendRequest() {
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.POST, req_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    Pharmacie pharma = new Pharmacie();
                    Garde garde=new Garde();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                       pharma.setNom_pharmacie(jsonObject.getString("nom_pharmacie"));
                        pharma.setNumTel(jsonObject.getString("numTel"));
                        pharma.setSite(jsonObject.getString("site"));
                        pharma.setAdresse(jsonObject.getString("adresse"));

                        byte[] decodedString = Base64.decode(jsonObject.getString("image"), Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        pharma.setImage(decodedImage);

                   //     pharma.setImage(jsonObject.get(valueOf(bmp1)));

                        garde.setPharmacie(pharma);
                        Date dateGarde=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date"));
                        garde.setDate(dateGarde);

                        System.out.println(garde.getPharmacie().getNom_pharmacie()+""+garde.getPharmacie().getNumTel()+""+garde.getPharmacie().getSite()+""+garde.getPharmacie().getAdresse());
                        System.out.println(garde.getDate());
                        System.out.println("-----------------------------------------");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception ee){
                        ee.printStackTrace();

                    }
                   // pharmacies.add(pharma);
                    gardes.add(garde);
                }
               // mAdapter = new Adapter(Home.this, pharmacies);
                mAdapter = new Adapter(Home.this, gardes);
                recyclerView.setAdapter(mAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley Error :", valueOf(error));
            }
        });
        rq.add(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        GoogleMapActivity googlMap=new GoogleMapActivity();
        int id = item.getItemId();
        if(id == R.id.ajouPharGarde){
            Intent intent=new Intent(getApplicationContext(),AddGarde.class);
            startActivity(intent);
        }
        if(id == R.id.ajouPhar){
            Intent intent=new Intent(getApplicationContext(),AjouterPharmacie.class);
            startActivity(intent);
        }
        if(id == R.id.pharmacieGarde){
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
        if(id==R.id.pharmacieAproximit){
            Intent intent=new Intent(getApplicationContext(),GoogleMapActivity.class);
            String pharma="pharmacy";
            intent.putExtra("param",pharma);
            startActivity(intent);
        }
        if(id==R.id.hospitalAproximit){
            Intent intent=new Intent(getApplicationContext(),GoogleMapActivity.class);
            String hopital="hospital";
            intent.putExtra("param",hopital);
            startActivity(intent);
        }
        if(id==R.id.notification){
            Intent add=new Intent(getApplicationContext(),ListeImage.class);
            startActivity(add);
        }
        if(id==R.id.aide){
            showCustomDialog();
        }
        if(id==R.id.Deconnexion){
            SharedPreferences.Editor prefsEditor = getSharedPreferences ("type_user_prefs",MODE_PRIVATE).edit();
            prefsEditor.putBoolean("login",false);
            prefsEditor.apply();
            Intent add=new Intent(getApplicationContext(),Login.class);
            startActivity(add);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.help_activity, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),Home.class);
        startActivity(intent);
    }
}

