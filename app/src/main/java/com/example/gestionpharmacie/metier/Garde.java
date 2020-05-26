package com.example.gestionpharmacie.metier;

import java.util.Date;

public class Garde {
    private Date date;
    private Pharmacie pharmacie;

    public Garde(Date date, Pharmacie pharmacie) {
        this.date = date;
        this.pharmacie = pharmacie;
    }

    public Date getDate() {
        return date;
    }

    public Pharmacie getPharmacie() {
        return pharmacie;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPharmacie(Pharmacie pharmacie) {
        this.pharmacie = pharmacie;
    }

    public Garde() {
    }


}
