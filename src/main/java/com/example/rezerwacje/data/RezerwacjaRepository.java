package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;

import java.util.Date;
import java.util.List;

public interface RezerwacjaRepository {
    Rezerwacja znajdzRezerwacje(int id);

    List<Date[]> znajdzTerminyRezerwacji(Pokoj pokoj);

    void dodajRezerwacje(Rezerwacja rezerwacja);

    Integer znajdzIdRezerwacji(Rezerwacja rezerwacja);

    List<Rezerwacja> znajdzRezerwacjeHotelu(String nazwaKierownika);

    void usunRezerwacje(int idRezerwacji);
}
