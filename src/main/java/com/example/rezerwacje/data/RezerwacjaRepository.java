package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.RezerwacjaForm;

import java.util.Date;
import java.util.List;

public interface RezerwacjaRepository {
    List<Rezerwacja> znajdzRezerwacje(Hotel hotel);

    List<Rezerwacja> znajdzRezerwacje(Pokoj pokoj);

    Rezerwacja znajdzRezerwacje(int id);

    List<Date[]> znajdzTerminyRezerwacji(Pokoj pokoj);

    List<Rezerwacja> znajdzRezerwacjePracownik(Uzytkownik uzytkownik);

    Rezerwacja usunRezerwacje(Rezerwacja rezerwacja);

    void dodajRezerwacje(Rezerwacja rezerwacja);

    void modyfikujRezerwacje(Rezerwacja rezerwacja, Uzytkownik uzytkownik);

    Integer znajdzIdRezerwacji(Rezerwacja rezerwacja);
}
