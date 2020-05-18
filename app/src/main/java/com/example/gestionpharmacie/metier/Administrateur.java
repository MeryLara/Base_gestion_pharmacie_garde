package com.example.gestionpharmacie.metier;

public class Administrateur extends  Personne {

    private int id_admin;

    public Administrateur(int id, String nom, String prenom, String email) {
        super(id, nom, prenom, email);
    }

    public Administrateur(String nom, String prenom, String email, int id_admin) {
        super(nom, prenom, email);
        this.id_admin = id_admin;
    }

    public Administrateur(String nom, String prenom, String email) {
        super(nom, prenom, email);
    }
}
