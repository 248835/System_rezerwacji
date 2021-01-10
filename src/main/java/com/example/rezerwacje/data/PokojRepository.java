package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;

import java.util.List;

public interface PokojRepository {
    List<Pokoj> znajdzPokoje(Hotel hotel);

    List<Pokoj> znajdzPokojeNazwaKierownika(String nazwaKierownika);

    Pokoj znajdzPokoj(int id);
}
