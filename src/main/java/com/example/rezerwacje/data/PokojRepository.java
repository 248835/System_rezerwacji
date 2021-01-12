package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.web.forms.PokojForm;

import java.util.List;

public interface PokojRepository {
    List<Pokoj> znajdzPokoje(Hotel hotel);

    List<Pokoj> znajdzPokojeNazwaKierownika(String nazwaKierownika);

    Pokoj znajdzPokoj(int id);

    void dodajPokoj(Pokoj pokoj, int id_hotelu);

    void zmienDanePokoju(PokojForm pokojDane, int id_pokoju);

    void usunPokoj(int id);
}
