package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class RezerwacjaForm {
    private Hotel hotel;
    private Pokoj pokoj;

    @NotNull(message = "{data.null}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poczatekRezerwacji;

    @NotNull(message = "{data.null}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date koniecRezerwacji;

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
        return "RezerwacjaForm{" +
                "hotel=" + hotel +
                ", pokoj=" + pokoj +
                ", poczatekRezerwacji=" + poczatekRezerwacji +
                ", koniecRezerwacji=" + koniecRezerwacji +
                '}';
    }
}
