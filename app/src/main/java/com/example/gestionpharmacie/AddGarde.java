package com.example.gestionpharmacie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AddGarde extends AppCompatActivity {

    Button btnAjouter,btnScanner,buttonOk;
    AutoCompleteTextView nomPharmacie,ville,adresse;

    TextView txtAjouterPharmacie;

    String selectedVille=null;
    String selectedPharma=null;

    Map<String, String> listPharmaAdresse = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garde);

        SharedPreferences prefs = getSharedPreferences ("type_user_prefs",MODE_PRIVATE);
        String userType = prefs.getString("userType","user");
        Boolean login=prefs.getBoolean("login",false);

        if(!login){
            Intent add=new Intent(getApplicationContext(),Login.class);
            startActivity(add);

        }

        getSupportActionBar().setTitle("Ajouter pharmacie garde");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtAjouterPharmacie=findViewById(R.id.txtAjouterPharmacie);
        txtAjouterPharmacie.setText(Html.fromHtml("<p><u>Ajouter Pharmacie</u></p>"));
        txtAjouterPharmacie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(getApplicationContext(),AjouterPharmacie.class);
                startActivity(add);
            }
        });


     /*   SharedPreferences prefs = getSharedPreferences ("type_user_prefs",MODE_PRIVATE);
        String userType = prefs.getString("userType","user");*/

        if(userType.equals("user")) {
            txtAjouterPharmacie.setVisibility(View.GONE);
        }
        buttonOk=(Button)findViewById(R.id.buttonOk);
        btnAjouter=(Button)findViewById(R.id.btnAjouter);
        btnScanner=(Button)findViewById(R.id.btnScanner);

        nomPharmacie=(AutoCompleteTextView)findViewById(R.id.edtNomPharmaGarde);
        ville=(AutoCompleteTextView)findViewById(R.id.edtVilleGarde);
        adresse=(AutoCompleteTextView)findViewById(R.id.edtAdresseGarde);

        btnAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnregistreGarde();
                showCustomDialog();
            }
        });

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(getApplicationContext(),ScannerImage.class);
                startActivity(add);
            }
        });

        getJSON("http://192.168.1.162/android/pharmacie/listeVile.php");
    }
    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
        }
    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] tabVille = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            tabVille[i] = obj.getString("ville");
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tabVille);
        ville.setAdapter(arrayAdapter);
        ville.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                nomPharmacie.setEnabled(false);
                nomPharmacie.setText("");
                adresse.setText("");
                return false;
            }
        });
        ville.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nomPharmacie.setEnabled(true);
                nomPharmacie.setText("");
                adresse.setText("");
                selectedVille= arrayAdapter.getItem(position).toString();
                RecupererPharmacie pharma=new RecupererPharmacie();
                pharma.execute(selectedVille);
            }
        });
    }
    private void loadPharmaIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] tabpharma = new String[jsonArray.length()];
        listPharmaAdresse.clear();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            tabpharma[i] = obj.getString("nom_pharmacie");
            listPharmaAdresse.put(obj.getString("nom_pharmacie"), obj.getString("adresse"));
        }
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tabpharma);
        nomPharmacie.setAdapter(arrayAdapter);
        nomPharmacie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPharma= arrayAdapter.getItem(position).toString();
                adresse.setText("");
                adresse.setText(listPharmaAdresse.get(selectedPharma));

            }
        });
    }
    private void getJSON(final String urlWebService)
    {
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
           //     Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try
                {
                    loadIntoListView(s);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {

                try
                {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null)
                    {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e)
                {

                    return null;
                }
            }
        }
        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
class RecupererPharmacie extends AsyncTask<String, Integer, String>
{
    HashMap<String, String> postDataParams;
    @Override
    protected String doInBackground(String... args)
    {
        String ville = args[0];

        try {
            URL url =new URL ("http://192.168.1.162/android/pharmacie/pharmacie.php");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json; utf-8");
            con.setRequestProperty("Accept","application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\"ville\": \""+ville+"\"}";

          //  System.out.println(jsonInputString);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected void onPostExecute(String s)
    {
        try {
            loadPharmaIntoListView(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
    public void EnregistreGarde()
    {
        String snompharma=nomPharmacie.getText().toString();

        InsertPharma insert =new InsertPharma();
        insert.execute(snompharma);
    }

    class InsertPharma extends AsyncTask<String, Integer, String> {
        HashMap<String, String> postDataParams;
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... args) {

            String action= "ajouter";
            String nomPharmacie = args[0];

            try {
                URL url =new URL ("http://192.168.1.162/android/pharmacie/InsertPharmacieGarde.php");
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json; utf-8");
                con.setRequestProperty("Accept","application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"action\": \""+action+"\", \"nomPharmacie\": \""+nomPharmacie+"\"}";

                try(OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
          // Toast.makeText(AddGarde.this,s,Toast.LENGTH_LONG).show();

        }

    }
    }
