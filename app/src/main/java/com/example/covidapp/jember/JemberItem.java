package com.example.covidapp.jember;

public class JemberItem {
    private String kecamatanTittle;
    private String kecamatanPositif;
    private String kecamatanOdr;
    private String kecamatanOdp;
    private String kecamatanPdp;

    public JemberItem(String kecamatanTittle, String kecamatanPositif, String kecamatanOdr, String kecamatanOdp, String kecamatanPdp) {
        this.kecamatanTittle = kecamatanTittle;
        this.kecamatanPositif = kecamatanPositif;
        this.kecamatanOdr = kecamatanOdr;
        this.kecamatanOdp = kecamatanOdp;
        this.kecamatanPdp = kecamatanPdp;
    }

    public String getKecamatanTittle() {
        return kecamatanTittle;
    }

    public String getKecamatanPositif() {
        return kecamatanPositif;
    }

    public String getKecamatanOdr() {
        return kecamatanOdr;
    }

    public String getKecamatanOdp() {
        return kecamatanOdp;
    }

    public String getKecamatanPdp() {
        return kecamatanPdp;
    }

    public void setKecamatanTittle(String kecamatanTittle) {
        this.kecamatanTittle = kecamatanTittle;
    }

    public void setKecamatanPositif(String kecamatanPositif) {
        this.kecamatanPositif = kecamatanPositif;
    }

    public void setKecamatanOdr(String kecamatanOdr) {
        this.kecamatanOdr = kecamatanOdr;
    }

    public void setKecamatanOdp(String kecamatanOdp) {
        this.kecamatanOdp = kecamatanOdp;
    }

    public void setKecamatanPdp(String kecamatanPdp) {
        this.kecamatanPdp = kecamatanPdp;
    }
}
