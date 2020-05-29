package com.example.gestionpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionpharmacie.metier.Adapter;

import com.example.gestionpharmacie.metier.Garde;
import com.example.gestionpharmacie.metier.Pharmacie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;


public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private  RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Garde> gardes;

    //-----------------pop up

    FloatingActionButton addGarde,map ;



    RequestQueue rq;
    String req_url="http://192.168.1.162/android/pharmacie/listpharmacie.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



//-------------------------------------------------------------------------------
       drawerLayout=(DrawerLayout) findViewById(R.id.draw);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

                        garde.setPharmacie(pharma);
                        garde.getPharmacie().setNom_pharmacie(jsonObject.getString("nom_pharmacie"));
                        garde.getPharmacie().setNumTel(jsonObject.getString("numTel"));
                        garde.getPharmacie().setSite(jsonObject.getString("site"));
                        garde.getPharmacie().setAdresse(jsonObject.getString("adresse"));

                        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("date"));
                        garde.setDate(date1);

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
                Log.i("Volley Error :", String.valueOf(error));
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



}

