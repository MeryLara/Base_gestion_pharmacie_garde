package com.example.gestionpharmacie.metier;

public class SimpleUser extends Personne {

    private int id_uer;

    public SimpleUser(int id, String nom, String prenom, String email, int id_uer) {
        super(id, nom, prenom, email);
        this.id_uer = id_uer;
    }

    public SimpleUser(String nom, String prenom, String email, int id_uer) {
        super(nom, prenom, email);
        this.id_uer = id_uer;
    }

    public SimpleUser(String nom, String prenom, String email) {
        super(nom, prenom, email);

    }
}
