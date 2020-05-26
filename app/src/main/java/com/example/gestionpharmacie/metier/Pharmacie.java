package com.example.gestionpharmacie.metier;

import android.widget.ImageView;

public class Pharmacie {
    private int id_pharmacie;
    private String nom_pharmacie;
    private String numTel;
    private String site;
    private String adresse;
    private ImageView image;


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


    public void setId_pharmacie(int id_pharmacie) {
        this.id_pharmacie = id_pharmacie;
    }

    public void setNom_pharmacie(String nom_pharmacie) {
        this.nom_pharmacie = nom_pharmacie;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Pharmacie() {

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

    public Pharmacie(String nom_pharmacie, String numTel) {
        this.nom_pharmacie = nom_pharmacie;
        this.numTel = numTel;
    }

    public Pharmacie(String nom_pharmacie, String numTel, String site, String adresse) {
        this.nom_pharmacie = nom_pharmacie;
        this.numTel = numTel;
        this.site = site;
        this.adresse = adresse;
    }
}
