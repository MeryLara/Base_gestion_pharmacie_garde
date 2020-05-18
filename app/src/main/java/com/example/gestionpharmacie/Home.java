package com.example.gestionpharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.gestionpharmacie.metier.Adapter;
import com.example.gestionpharmacie.metier.Adresse;
import com.example.gestionpharmacie.metier.Pharmacie;

import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawerLayout=(DrawerLayout) findViewById(R.id.draw);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RecyclerView recyclerView=findViewById(R.id.rv_list);
        List<Pharmacie> mlist =new ArrayList<>();

         mlist.add(new Pharmacie("pharma", "7889", "jjjdfkk", "hay elmassira"));
        mlist.add(new Pharmacie("pharma", "7889", "jjjdfkk", "hay elmassira"));

        Adapter adapter=new Adapter(this,mlist);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




}

