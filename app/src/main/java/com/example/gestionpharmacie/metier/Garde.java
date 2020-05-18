package com.example.gestionpharmacie.metier;

import java.util.Date;

public class Garde {
    private Date date;
    private Pharmacie pharmacie;

    public Garde(Date date, Pharmacie pharmacie) {
        this.date = date;
        this.pharmacie = pharmacie;
    }
}
