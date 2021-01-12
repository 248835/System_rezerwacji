package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.web.forms.HotelForm;

import java.util.List;

public interface HotelRepository {
    Hotel znajdzHotelKierownik(String nazwaKierownika);

    List<Hotel> znajdzHoteleMiasto(String miasto);

    Hotel znajdzHotel(String miasto, String nazwa);

    Hotel znajdzHotel(int id);

    Hotel znajdzHotel(Pokoj pokoj);

    void dodajHotel(Hotel hotel, String nazwaKierownika);
	
	void zmienHotel(HotelForm hotelDane, String kierownik);
	
	void usunHotel(String nazwaKierownika);
}
