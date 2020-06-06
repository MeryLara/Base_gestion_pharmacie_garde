package com.example.gestionpharmacie.metier;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpharmacie.AddGarde;
import com.example.gestionpharmacie.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AdapterListeImage extends RecyclerView.Adapter<AdapterListeImage.myListViewHolser> {

    Context mcontext;
    List<ImageCapture> images;



    public AdapterListeImage(Context mcontext, List<ImageCapture> images) {
        this.mcontext = mcontext;
        this.images = images;
    }

    @NonNull
    @Override
    public myListViewHolser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.carde_item_image,parent,false);
        myListViewHolser viewHolser=new myListViewHolser(v);

        return viewHolser;
    }


    @Override
    public void onBindViewHolder(@NonNull myListViewHolser holder, int position) {

        //holder.itemView.setTag(images.get(position));
        ImageCapture imageCapture=images.get(position);
        holder.txtVille.setText(imageCapture.getVille());
        holder.imggarde.setImageBitmap(imageCapture.getImage());
        holder.btnrefuser.setTag(imageCapture.getId_image());

    }


    @Override
    public int getItemCount() {
        return images.size();
    }


    public class  myListViewHolser extends RecyclerView.ViewHolder{


        TextView txtVille;
        ImageView imggarde;
        Button btnrefuser,btnapprouver;

        public myListViewHolser(@NonNull final View itemView) {
            super(itemView);

            txtVille= (TextView)itemView.findViewById(R.id.txtVillePharmaGarde);
            imggarde= (ImageView) itemView.findViewById(R.id.image_garde);
            btnrefuser=itemView.findViewById(R.id.btnRefuser);
            btnapprouver=itemView.findViewById(R.id.btnApprouver);


           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageCapture img= (ImageCapture) v.getTag();
                    // System.out.println((ImageCapture) v.getTag());
                    Toast.makeText(v.getContext(),img.getId_image(), Toast.LENGTH_SHORT).show();
                  //  AlertDialog.Builder builder=new AlertDialog.Builder(mcontext.getApplicationContext());
                  //  ImageView im=new ImageView(mcontext.getApplicationContext());
                //    builder.setView(v);
                //    builder.create().show();
                }
            });

            */

            btnrefuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                /*    AlertDialog.Builder dialog = new AlertDialog.Builder(mcontext.getApplicationContext());
                    dialog.setTitle("Refuser");
                    dialog.setMessage("vous voulez vraiment supprimer cette information de pharmacies de garde ?");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int
                                        id) {
                                    int captureImageId= (int) v.getTag();
                                    System.out.println(captureImageId);
                                    DeleteImage deleteImage=new DeleteImage();
                                    deleteImage.execute(captureImageId);

                                }
                            });
                    dialog.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    dialog.cancel();
                                }
                            });


                    AlertDialog alert = dialog.create();
                    alert.show();*/

                      int captureImageId= (int) v.getTag();
                    System.out.println(captureImageId);
                    DeleteImage deleteImage=new DeleteImage();
                    deleteImage.execute(captureImageId);
                    Toast.makeText(mcontext.getApplicationContext(),"Ces information sont bien supprimer", Toast.LENGTH_SHORT).show();
                }
            });

            btnapprouver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add=new Intent(mcontext.getApplicationContext(), AddGarde.class);
                    mcontext.startActivity(add);

                }
            });

        }
    }

    class DeleteImage extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... args) {
            int id = args[0];

            try{
                URL url =new URL ("http://192.168.1.162/android/pharmacie/refuserImage.php");
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type","application/json; utf-8");
                con.setRequestProperty("Accept","application/json");
                con.setDoOutput(true);
                String jsonInputString = "{\"id_image\": \""+id+"\"}";

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
        protected void onPostExecute(String s) {
            System.out.println(s);
        }
    }


}

