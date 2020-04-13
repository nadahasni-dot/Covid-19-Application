package com.example.covidapp.provinsi;

public class ProvinsiItem {
    private String provinsiTittle;
    private String provinsiPositif;
    private String provinsiSembuh;
    private String provinsiMeninggal;

    public ProvinsiItem(String tittle, String positif, String sembuh, String meninggal){
        this.provinsiTittle = tittle;
        this.provinsiPositif = positif;
        this.provinsiSembuh = sembuh;
        this.provinsiMeninggal = meninggal;
    }

    public String getProvinsiTittle(){
        return provinsiTittle;
    }

    public String getProvinsiPositif(){
        return provinsiPositif;
    }

    public String getProvinsiSembuh(){
        return provinsiSembuh;
    }

    public String getProvinsiMeninggal(){
        return provinsiMeninggal;
    }
}
