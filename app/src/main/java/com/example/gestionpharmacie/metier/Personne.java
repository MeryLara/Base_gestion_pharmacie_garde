package com.example.gestionpharmacie.metier;

public class Personne {

    private int id;
    private String nom;
    private String prenom;
    private String email;

    private Authentification authentification;

    public Personne(int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Personne(String nom, String prenom, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public Personne(int id, String nom, String prenom, String email, Authentification authentification) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.authentification = authentification;
    }

    public Personne(String nom, String prenom, String email, Authentification authentification) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.authentification = authentification;
    }
}
