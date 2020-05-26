package com.example.gestionpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {

    EditText edtuser,edtPass;
    TextView txtMessageErreur;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtuser=findViewById(R.id.edtusernameConnect);
        edtPass=findViewById(R.id.edtpasswordConnect);
        btnLogin=findViewById(R.id.btnConnect);

    }


    public void OnrConnect(View v) {

        String suser=edtuser.getText().toString();
        String pass=edtPass.getText().toString();
        if(suser.matches("")){
            //edtuser.setBackgroundColor(Color.MAGENTA);
            Toast.makeText(Login.this, "le champs username est vide ",Toast.LENGTH_SHORT).show();


        }else  if(pass.matches("")){

            Toast.makeText(Login.this, "le champs mot passe est vide ",Toast.LENGTH_SHORT).show();

        }else {
            Login.connect connecter=new Login.connect();
            connecter.execute(suser,pass);
        }


    }


    class connect extends AsyncTask<String, Integer, String> {
        HashMap<String, String> postDataParams;


        @Override
        protected String doInBackground(String... args) {

            String action= "login";
            String user = args[0];
            String pass = args[1];
            System.out.println(user);
            System.out.println(pass);



            try {
                URL url =new URL ("http://192.168.1.162/android/pharmacie/compte.php");
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json; utf-8");
                con.setRequestProperty("Accept","application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"action\": \""+action+"\", \"username\": \""+user+"\",\"password\" : \""+pass+"\"}";


                System.out.println(jsonInputString);
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


                  /*  JSONObject jsonResponse = new JSONObject(response.toString());
                    if(!jsonResponse.getBoolean("success")){
                        txtMessageErreur=findViewById(R.id.txtMessageErreur);
                        txtMessageErreur.setText("Utilisateur n'exist pas  ");


                        System.out.println(jsonResponse.getString("message"));
                    } else {
                        System.out.println("login ok =======");
                        Intent intent=new Intent(Login.this,Home.class);
                        startActivity(intent);
                    }*/
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
            txtMessageErreur=findViewById(R.id.txtMessageErreur);
            try {
                JSONObject jsonResponse = new JSONObject(s);
                if(!jsonResponse.getBoolean("success")){

                    txtMessageErreur.setText(" Utilisateur n'exist pas !!! ");


                } else {
                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);

                   

                }
            }catch (JSONException e){
                e.getStackTrace();
            }

        }


        }

    }
