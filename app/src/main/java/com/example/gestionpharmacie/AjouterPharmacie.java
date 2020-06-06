package com.example.gestionpharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class AjouterPharmacie extends AppCompatActivity implements View.OnClickListener{

    public static final String UPLOAD_URL = "http://192.168.1.162/android/pharmacie/InserPharmacie.php";
    public static final String UPLOAD_KEY = "image";
    public static final String Nom_KEY = "nom";
    public static final String TEL_KEY = "tel";
    public static final String SITE_KEY = "site";
    public static final String ADRESSE_KEY = "adresse";
    public static final String CODEÖSTAL_KEY = "codePostal";
    public static final String VILLE_KEY = "ville";
    public static final String PAYS_KEY = "pays";

    private int PICK_IMAGE_REQUEST = 1;
    private Button buttonChoose;
    private Button buttonUpload;

    private EditText edtPharmacie;
    private EditText edtTel;
    private EditText edtSite;
    private EditText edtAdresse;
    private EditText edtCodePostal;
    private EditText edtVille;
    private EditText edtPays;

    private ImageView imageView;
    private Bitmap bitmap;
    private Uri filePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_pharmacie);


        SharedPreferences prefs = getSharedPreferences ("type_user_prefs",MODE_PRIVATE);
        String userType = prefs.getString("userType","user");
        Boolean login=prefs.getBoolean("login",false);

        if(!login){
            Intent add=new Intent(getApplicationContext(),Login.class);
            startActivity(add);

        }

        getSupportActionBar().setTitle("Ajouter Pharmacie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        imageView = (ImageView) findViewById(R.id.imgViewPharma);
        edtPharmacie=(EditText)findViewById(R.id.edtPharmacie);
        edtTel=(EditText)findViewById(R.id.edtTel);
        edtSite=(EditText)findViewById(R.id.edtSite);
        edtAdresse=(EditText)findViewById(R.id.edtAdresse);
        edtCodePostal=(EditText)findViewById(R.id.edtCodePostal);
        edtVille=(EditText)findViewById(R.id.edtVille);
        edtPays=(EditText)findViewById(R.id.edtPays);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);


    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            String nom= edtPharmacie.getText().toString();
            String tel= edtTel.getText().toString();
            String site= edtSite.getText().toString();
            String adresse= edtAdresse.getText().toString();
            String cpostal= edtCodePostal.getText().toString();
            String ville= edtVille.getText().toString();
            String pays= edtPays.getText().toString();

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AjouterPharmacie.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put(VILLE_KEY, ville);
                data.put(Nom_KEY, nom);
                data.put(TEL_KEY, tel);
                data.put(SITE_KEY, site);
                data.put(ADRESSE_KEY, adresse);
                data.put(CODEÖSTAL_KEY, cpostal);
                data.put(PAYS_KEY, pays);

                String result = rh.sendPostRequest(UPLOAD_URL,data);
                System.out.println("----------------------------------------------------------------------");
                System.out.println(data);
                System.out.println("----------------------------------------------------------------------");
                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }


    @Override
    public void onClick(View v) {

        if (v == buttonChoose) {
           showFileChooser();
              Toast.makeText(AjouterPharmacie.this, "ok", Toast.LENGTH_SHORT).show();
        }
        if(v == buttonUpload){
           uploadImage();
            Intent add=new Intent(getApplicationContext(),Home.class);
            startActivity(add);
           // Toast.makeText(AjouterPharmacie.this, "ok", Toast.LENGTH_SHORT).show();

        }
    }
}
