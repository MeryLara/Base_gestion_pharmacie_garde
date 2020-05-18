package com.example.gestionpharmacie.metier;

public class ImageCapture {
    private int id_image;
    private String image;
    private SimpleUser simpleUser;

    public ImageCapture(int id_image, String image, SimpleUser simpleUser) {
        this.id_image = id_image;
        this.image = image;
        this.simpleUser = simpleUser;
    }

    public ImageCapture(String image, SimpleUser simpleUser) {
        this.image = image;
        this.simpleUser = simpleUser;
    }
}
