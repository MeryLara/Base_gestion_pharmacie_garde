package com.example.gestionpharmacie.metier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionpharmacie.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolser> {

    Context mcontext;
    List<Pharmacie> mData;

    public Adapter(Context mcontext, List<Pharmacie> mData) {
        this.mcontext = mcontext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mcontext);
        View v=inflater.inflate(R.layout.card_item,parent,false);
        return new myViewHolser(v);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolser holder, int position) {

        //holder.background_img.setImageResource("");
        holder.txtNom.setText(mData.get(position).getNom_pharmacie());
        holder.txtSite.setText(mData.get(position).getSite());
        holder.txtTel.setText(mData.get(position).getNumTel());
        holder.txtAdress.setText(mData.get(position).getAdresse());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class  myViewHolser extends RecyclerView.ViewHolder{


        ImageView background_img;
        TextView txtNom;
        TextView txtSite;
        TextView txtTel;
        TextView txtAdress;



        public myViewHolser(@NonNull View itemView) {
            super(itemView);

            background_img=itemView.findViewById(R.id.image_back);
           txtNom=itemView.findViewById(R.id.txtNomPharma);
            txtAdress=itemView.findViewById(R.id.txtAdressPharma);
            txtSite=itemView.findViewById(R.id.txtSitePharma);
            txtTel=itemView.findViewById(R.id.txtTelPharma);

        }
    }
}
