package com.example.gestionpharmacie.metier;

import android.widget.ImageView;

public class Pharmacie {
    private int id_pharmacie;
    private String nom_pharmacie;
    private String numTel;
    private ImageView image;
    private String site;
    private String adresse;

    public int getId_pharmacie() {
        return id_pharmacie;
    }

    public String getNom_pharmacie() {
        return nom_pharmacie;
    }

    public String getNumTel() {
        return numTel;
    }

    public ImageView getImage() {
        return image;
    }

    public String getSite() {
        return site;
    }

    public String getAdresse() {
        return adresse;
    }

    public Pharmacie(int id_pharmacie, String nom_pharmacie, String numTel, ImageView image, String site, String adresse) {
        this.id_pharmacie = id_pharmacie;
        this.nom_pharmacie = nom_pharmacie;
        this.numTel = numTel;
        this.image = image;
        this.site = site;
        this.adresse = adresse;
    }

    public Pharmacie(String nom_pharmacie, String numTel, ImageView image, String site, String adresse) {
        this.nom_pharmacie = nom_pharmacie;
        this.numTel = numTel;
        this.image = image;
        this.site = site;
        this.adresse = adresse;
    }

    public Pharmacie(String nom_pharmacie, String numTel, String site, String adresse) {
        this.nom_pharmacie = nom_pharmacie;
        this.numTel = numTel;
        this.site = site;
        this.adresse = adresse;
    }
}
