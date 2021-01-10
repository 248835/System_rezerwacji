package com.example.rezerwacje.data;

import com.example.rezerwacje.uzytkownik.Uzytkownik;

public interface UzytkownikRepository{
    Uzytkownik znajdzUzytkownika(String nazwa);

    void addUzytkownik(Uzytkownik uzytkownik);
}
