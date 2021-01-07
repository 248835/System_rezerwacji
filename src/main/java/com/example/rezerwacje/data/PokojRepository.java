package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PokojRepository {
    List<Pokoj> znajdzPokoje(Hotel hotel);

    Pokoj znajdzPokoj(int id);

    Map<Hotel, List<Pokoj>> znajdzOferty(String adres, Date poczatek, Date koniec);

    void dodajPokoj(Pokoj pokoj, Hotel hotel);

    void usunPokoj(Pokoj pokoj);

    void modyfikujPokoj(Pokoj pokoj);
}
