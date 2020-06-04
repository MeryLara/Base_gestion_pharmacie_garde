package com.example.gestionpharmacie.metier;

import android.graphics.Bitmap;

public class ImageCapture {
    private int id_image;
    private Bitmap image;
    private SimpleUser simpleUser;
    private String ville;

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getVille() {
        return ville;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public int getId_image() {
        return id_image;
    }

    public Bitmap getImage() {
        return image;
    }

    public SimpleUser getSimpleUser() {
        return simpleUser;
    }

    @Override
    public String toString() {
        return "ImageCapture{" +
                "id_image=" + id_image +
                ", ville='" + ville + '\'' +
                '}';
    }
}
