package com.example.rezerwacje.rezerwacja;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.Date;

public class Rezerwacja {
    private int id;
    private Hotel hotel;
    private Pokoj pokoj;
    private Uzytkownik uzytkownik;
    private Date poczatekRezerwacji;
    private Date koniecRezerwacji;

    public Rezerwacja(Hotel hotel, Pokoj pokoj, Uzytkownik uzytkownik, Date poczatekRezerwacji, Date koniecRezerwacji) {
        this.hotel = hotel;
        this.pokoj = pokoj;
        this.uzytkownik = uzytkownik;
        this.poczatekRezerwacji = poczatekRezerwacji;
        this.koniecRezerwacji = koniecRezerwacji;
    }

    public Rezerwacja(int id, Hotel hotel, Pokoj pokoj, Uzytkownik uzytkownik, Date poczatekRezerwacji, Date koniecRezerwacji) {
        this.id = id;
        this.hotel = hotel;
        this.pokoj = pokoj;
        this.uzytkownik = uzytkownik;
        this.poczatekRezerwacji = poczatekRezerwacji;
        this.koniecRezerwacji = koniecRezerwacji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Pokoj getPokoj() {
        return pokoj;
    }

    public void setPokoj(Pokoj pokoj) {
        this.pokoj = pokoj;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Date getPoczatekRezerwacji() {
        return poczatekRezerwacji;
    }

    public void setPoczatekRezerwacji(Date poczatekRezerwacji) {
        this.poczatekRezerwacji = poczatekRezerwacji;
    }

    public Date getKoniecRezerwacji() {
        return koniecRezerwacji;
    }

    public void setKoniecRezerwacji(Date koniecRezerwacji) {
        this.koniecRezerwacji = koniecRezerwacji;
    }

    @Override
    public String toString() {
        return "Rezerwacja{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", pokoj=" + pokoj +
                ", uzytkownik=" + uzytkownik +
                ", poczatekRezerwacji=" + poczatekRezerwacji +
                ", koniecRezerwacji=" + koniecRezerwacji +
                '}';
    }
}
