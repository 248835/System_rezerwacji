package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;

import java.util.List;

public interface HotelRepository {
    Hotel znajdzHotelKierownik(String nazwaKierownika);

    List<Hotel> znajdzHoteleMiasto(String miasto);

    Hotel znajdzHotel(String miasto, String nazwa);
    
    void dodajHotel(Hotel hotel);
	
	void zmienHotel(HotelForm hotelDane, String kierownik);
	
	void usunHotel(Hotel hotel);
}
