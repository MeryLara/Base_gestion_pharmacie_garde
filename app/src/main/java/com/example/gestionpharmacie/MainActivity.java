package com.example.gestionpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText nom, prenom, email, password,username,tel;
    Button b1, btnLogEnreg;
    Spinner snptype;
    TextView txtmessg;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Spinner element
        snptype = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snptype.setAdapter(adapter);

        username=findViewById(R.id.edtusername);
        nom = findViewById(R.id.edtNom);
        prenom = findViewById(R.id.edtprenom);
        email = findViewById(R.id.edtemail);

        password = findViewById(R.id.edtpass);
        tel = findViewById(R.id.edtTel);
        b1 = findViewById(R.id.btnEnreg);

        btnLogEnreg = findViewById(R.id.btnLoginEnreg);
        btnLogEnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Ne peut pas etre vide");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("S'il vous plaît, mettez une adresse email valide");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    public void Onregister(View v) {

        String suser=username.getText().toString();
        String snom=nom.getText().toString();
        String sprenom=prenom.getText().toString();
        String semail=email.getText().toString();


        String pass=password.getText().toString();
        String stel=tel.getText().toString();
        String type=snptype.getSelectedItem().toString();

        if(suser.matches("")){
            username.setBackgroundColor(Color.MAGENTA);

        }else
        if(snom.matches("")){
            nom.setBackgroundColor(Color.YELLOW);
        }else
        if(sprenom.matches("")){
            prenom.setBackgroundColor(Color.MAGENTA);
        }else
        if(semail.matches("")){
            email.setBackgroundColor(Color.MAGENTA);
        }else
        if(pass.matches("")){
            password.setBackgroundColor(Color.MAGENTA);
        }else
        if(stel.matches("")){
            tel.setBackgroundColor(Color.MAGENTA);
        }else{
            if (!validateEmail()) {
                return;
            }
            InsertUser insertUser=new InsertUser();
            insertUser.execute(suser,pass,snom,sprenom,semail,type,stel);
        }

    }

    class InsertUser extends AsyncTask<String, Integer, String> {

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... args) {

            String action= "register";
            String user = args[0];
            String pass = args[1];
            String nom = args[2];
            String prenom = args[3];
            String email = args[4];
            String type = args[5];
            String tel = args[6];

        try {
                URL url =new URL ("http://192.168.1.162/android/pharmacie/compte.php");
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json; utf-8");
                con.setRequestProperty("Accept","application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"action\": \""+action+"\", \"username\": \""+user+"\",\"type\": \""+type+"\", \"nom\": \""+nom+"\", \"prenom\" : \""+prenom+"\",\"numTel\" :\""+tel+"\" ,\"email\" : \""+email+"\",\"password\" : \""+pass+"\"}";

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
            //Toast.makeText(MainActivity.this, "Insertion des données",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {

            txtmessg=findViewById(R.id.txtMessage);
            try {
                JSONObject jsonResponse = new JSONObject(s);
                if(!jsonResponse.getBoolean("success")){

                    txtmessg.setText("Non enregistre ");
                    txtmessg.setBackgroundColor(Color.RED);

                } else {

                        txtmessg.setText("enregistre ok");
                        //vider les champs
                    txtmessg.setBackgroundColor(Color.GREEN);
                    nom.setText("");
                    prenom.setText("");
                    email.setText("");
                    password.setText("");
                    username.setText("");
                    tel.setText("");




                    }
            }catch (JSONException e){
                e.getStackTrace();
            }

        }
}

}