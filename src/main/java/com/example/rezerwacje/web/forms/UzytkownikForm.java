package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.uzytkownik.Uzytkownik;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UzytkownikForm {
    @Size(min=2, max=20, message="{nazwa.size}")
    private String nazwa;

    @Size(min=5, max=20, message="{haslo.size}")
    private String haslo;

    @Size(min=2, max=20, message="{imie.size}")
    private String imie;

    @Size(min=2, max=20, message="{nazwisko.size}")
    private String nazwisko;

    @NotBlank(message = "{rola.blank}")
    private String rola;

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

    public Uzytkownik toUzytkownik(){
        return new Uzytkownik(nazwa,haslo,imie,nazwisko,rola);
    }

    @Override
    public String toString() {
        return "UzytkownikForm{" +
                "nazwa='" + nazwa + '\'' +
                ", haslo='" + haslo + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", rola='" + rola + '\'' +
                '}';
    }
}
