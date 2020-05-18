package com.example.gestionpharmacie.metier;

public class Authentification {

    private  int id;
    private  String username;
    private String password ;

    public Authentification(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Authentification(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
