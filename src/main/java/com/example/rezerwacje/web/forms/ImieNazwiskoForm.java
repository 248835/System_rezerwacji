package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.uzytkownik.Uzytkownik;

import javax.validation.constraints.Size;

public class ImieNazwiskoForm {
    @Size(min=2, max=20, message="{imie.size}")
    private String imie;

    @Size(min=2, max=20, message="{nazwisko.size}")
    private String nazwisko;

    public ImieNazwiskoForm(Uzytkownik uzytkownik){
        this.imie = uzytkownik.getImie();
        this.nazwisko = uzytkownik.getNazwisko();
    }

    public ImieNazwiskoForm() {
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

    @Override
    public String toString() {
        return "ImieNazwiskoForm{" +
                "imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                '}';
    }
}
