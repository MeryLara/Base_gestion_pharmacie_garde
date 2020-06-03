package com.example.gestionpharmacie.metier;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpharmacie.R;
import com.example.gestionpharmacie.map.GoogleMapActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolser> {

    Context mcontext;
    List<Garde> gardes;
    Button map;


    public Adapter(Context mcontext, List<Garde> gardes) {
        this.mcontext = mcontext;
        this.gardes = gardes;
    }
    @NonNull
    @Override
    public myViewHolser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
      myViewHolser viewHolder=new myViewHolser(v);
      return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolser holder, int position) {

        holder.itemView.setTag(gardes.get(position));
        Garde garde=gardes.get(position);
        Pharmacie pharm=new Pharmacie();
        holder.txtNom.setText(garde.getPharmacie().getNom_pharmacie());
        holder.txtSite.setText(garde.getPharmacie().getSite());
        holder.txtTel.setText(garde.getPharmacie().getNumTel());
        holder.txtAdress.setText(garde.getPharmacie().getAdresse());
        DateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.txtDate.setText(shortDateFormat.format(garde.getDate()));
        holder.imgPharma.setImageBitmap(garde.getPharmacie().getImage());

    }
    @Override
    public int getItemCount() {
        //return mData.size();
        return  gardes.size();
    }
    public class  myViewHolser extends RecyclerView.ViewHolder{


        TextView txtNom;
        TextView txtSite;
        TextView txtTel;
        TextView txtAdress;
        TextView txtDate;
        ImageView imgPharma;

        public myViewHolser(@NonNull View itemView) {
            super(itemView);


           txtNom= (TextView)itemView.findViewById(R.id.txtNomPharma);
            txtAdress= (TextView)itemView.findViewById(R.id.txtAdressPharma);
            txtSite= (TextView)itemView.findViewById(R.id.txtSitePharma);
            txtTel= (TextView)itemView.findViewById(R.id.txtTelPharma);
            imgPharma= (ImageView) itemView.findViewById(R.id.image_back);
            txtDate= (TextView)itemView.findViewById(R.id.txtDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Pharmacie ph= (Pharmacie) v.getTag();
                    Garde g=(Garde) v.getTag();
                    Toast.makeText(v.getContext(), g.getPharmacie().getNom_pharmacie()+""+g.getPharmacie().getNumTel()+""+g.getPharmacie().getSite()+""+g.getPharmacie().getAdresse()+""+g.getDate(), Toast.LENGTH_SHORT).show();

                }
            });

            map=itemView.findViewById(R.id.btnMap);
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add=new Intent(mcontext.getApplicationContext(), GoogleMapActivity.class);
                    mcontext.startActivity(add);
                }
            });

        }
    }
}
