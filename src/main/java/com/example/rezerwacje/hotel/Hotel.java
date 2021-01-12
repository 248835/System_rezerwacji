package com.example.rezerwacje.hotel;

public class Hotel {
    private String nazwa;
    private String miasto;
    private String ulica;
    private int ocena;

    private int id;

    public Hotel(String nazwa, String miasto, String ulica, int ocena) {
        this.nazwa = nazwa;
        this.miasto = miasto;
        this.ulica = ulica;
        this.ocena = ocena;
    }

    public Hotel(String nazwa, String miasto, String ulica, int ocena, int id) {
        this.nazwa = nazwa;
        this.miasto = miasto;
        this.ulica = ulica;
        this.ocena = ocena;
        this.id = id;
    }

    public Hotel() {
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nazwa='" + nazwa + '\'' +
                ", miasto='" + miasto + '\'' +
                ", ulica='" + ulica + '\'' +
                ", ocena=" + ocena +
                ", id=" + id +
                '}';
    }
}
