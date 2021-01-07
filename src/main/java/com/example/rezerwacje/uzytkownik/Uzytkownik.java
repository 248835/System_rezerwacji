package com.example.rezerwacje.uzytkownik;

public class Uzytkownik {
    private String nazwa;

    private String haslo;

    private String imie;

    private String nazwisko;

    private String rola;

    public Uzytkownik(String nazwa, String haslo, String imie, String nazwisko, String rola) {
        this.nazwa = nazwa;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.rola = rola;
    }

    public Uzytkownik(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }
}
