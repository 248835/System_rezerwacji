package com.example.rezerwacje.hotel;

public class Pokoj {
    private int id;
    private int numer;
    private int rodzaj;
    private int rozmiar;
    private int cena;

    public Pokoj(int id, int nr, int rodzaj, int rozmiar, int cena) {
        this.id = id;
        this.numer = nr;
        this.rodzaj = rodzaj;
        this.rozmiar = rozmiar;
        this.cena = cena;
    }

    public Pokoj(int nr, int rodzaj, int rozmiar, int cena) {

        this.numer = nr;
        this.rodzaj = rodzaj;
        this.rozmiar = rozmiar;
        this.cena = cena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public int getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(int rodzaj) {
        this.rodzaj = rodzaj;
    }

    public int getRozmiar() {
        return rozmiar;
    }

    public void setRozmiar(int rozmiar) {
        this.rozmiar = rozmiar;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }
}
