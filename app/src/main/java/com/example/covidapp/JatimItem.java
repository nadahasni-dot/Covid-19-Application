package com.example.covidapp;

public class JatimItem {
    private String kotaTittle;
    private String kotaUpdate;
    private String kotaPositif;
    private String kotaSembuh;
    private String kotaMeninggal;
    private String kotaOdr;
    private String kotaOtg;
    private String kotaOdp;
    private String kotaPdp;

    public JatimItem(String kotaTittle, String kotaUodate, String kotaPositif, String kotaSembuh, String kotaMeninggal, String kotaOdr, String kotaOtg, String kotaOdp, String kotaPdp) {
        this.kotaTittle = kotaTittle;
        this.kotaUpdate = kotaUodate;
        this.kotaPositif = kotaPositif;
        this.kotaSembuh = kotaSembuh;
        this.kotaMeninggal = kotaMeninggal;
        this.kotaOdr = kotaOdr;
        this.kotaOtg = kotaOtg;
        this.kotaOdp = kotaOdp;
        this.kotaPdp = kotaPdp;
    }

    public String getKotaTittle() {
        return kotaTittle;
    }

    public String getKotaUodate() {
        return kotaUpdate;
    }

    public String getKotaPositif() {
        return kotaPositif;
    }

    public String getKotaSembuh() {
        return kotaSembuh;
    }

    public String getKotaMeninggal() {
        return kotaMeninggal;
    }

    public String getKotaOdr() {
        return kotaOdr;
    }

    public String getKotaOtg() {
        return kotaOtg;
    }

    public String getKotaOdp() {
        return kotaOdp;
    }

    public String getKotaPdp() {
        return kotaPdp;
    }

    public void setKotaTittle(String kotaTittle) {
        this.kotaTittle = kotaTittle;
    }

    public void setKotaUodate(String kotaUodate) {
        this.kotaUpdate = kotaUodate;
    }

    public void setKotaPositif(String kotaPositif) {
        this.kotaPositif = kotaPositif;
    }

    public void setKotaSembuh(String kotaSembuh) {
        this.kotaSembuh = kotaSembuh;
    }

    public void setKotaMeninggal(String kotaMeninggal) {
        this.kotaMeninggal = kotaMeninggal;
    }

    public void setKotaOdr(String kotaOdr) {
        this.kotaOdr = kotaOdr;
    }

    public void setKotaOtg(String kotaOtg) {
        this.kotaOtg = kotaOtg;
    }

    public void setKotaOdp(String kotaOdp) {
        this.kotaOdp = kotaOdp;
    }

    public void setKotaPdp(String kotaPdp) {
        this.kotaPdp = kotaPdp;
    }
}
