package com.example.gestionpharmacie.metier;

public class pharmacien extends Personne{

    private int id_pharmacie;
    private String numTel;
    private int matricul;

    public pharmacien(int id, String nom, String prenom, String email, int id_pharmacie, String numTel, int matricul) {
        super(id, nom, prenom, email);
        this.id_pharmacie = id_pharmacie;
        this.numTel = numTel;
        this.matricul = matricul;
    }

    public pharmacien(int id, String nom, String prenom, String email, String numTel, int matricul) {
        super(id, nom, prenom, email);
        this.numTel = numTel;
        this.matricul = matricul;
    }

    public pharmacien(String nom, String prenom, String email, String numTel, int matricul) {
        super(nom, prenom, email);
        this.numTel = numTel;
        this.matricul = matricul;
    }

    public pharmacien(String nom, String prenom, String email, int id_pharmacie, String numTel, int matricul) {
        super(nom, prenom, email);
        this.id_pharmacie = id_pharmacie;
        this.numTel = numTel;
        this.matricul = matricul;
    }
}
