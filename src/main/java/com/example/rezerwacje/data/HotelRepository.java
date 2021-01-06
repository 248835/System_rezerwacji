package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HotelRepository {
    List<Hotel> znajdzHotele(Uzytkownik uzytkownik);

    List<Hotel> znajdzHotele(String miasto);

    Hotel znajdzHotelPracownika(Uzytkownik pracownik);

    Hotel znajdzHotel(String miasto, String nazwa);

    void addHotel(Uzytkownik kierownik, Hotel hotel);

    void modyfikujHotel(Hotel hotel);

    void usunHotel(Hotel hotel);
}
