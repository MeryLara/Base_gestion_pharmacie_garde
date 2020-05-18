package com.example.gestionpharmacie.metier;

public class Adresse {
    private int id_adress;
    private String adresse;
    private int codePostal;
    private String ville;
    private String pays;
    private float atitude;
    private  float longitude;

    public Adresse(int id_adress, String adresse, int codePostal, String ville, String pays, float atitude, float longitude) {
        this.id_adress = id_adress;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.atitude = atitude;
        this.longitude = longitude;
    }

    public Adresse(String adresse, int codePostal, String ville, String pays, float atitude, float longitude) {
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.atitude = atitude;
        this.longitude = longitude;
    }
}
