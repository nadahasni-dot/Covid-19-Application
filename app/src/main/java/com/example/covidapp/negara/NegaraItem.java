package com.example.covidapp.negara;

import android.widget.TextView;

public class NegaraItem {
    private String negaraTittle;
    private String negaraPositif;
    private String negaraSembuh;
    private String negaraMeninggal;

    public NegaraItem(String tittle, String positif, String sembuh, String meninggal){
        this.negaraTittle = tittle;
        this.negaraPositif = positif;
        this.negaraSembuh = sembuh;
        this.negaraMeninggal = meninggal;
    }

    public String getNegaraTittle(){
        return negaraTittle;
    }

    public String getNegaraPositif(){
        return negaraPositif;
    }

    public String getNegaraSembuh(){
        return negaraSembuh;
    }

    public String getNegaraMeninggal(){
        return negaraMeninggal;
    }
}
