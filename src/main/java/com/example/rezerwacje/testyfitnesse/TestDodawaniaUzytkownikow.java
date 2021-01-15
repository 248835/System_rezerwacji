package com.example.rezerwacje.testyfitnesse;

import com.example.rezerwacje.data.UzytkownikRepositoryException;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import fit.ColumnFixture;

public class TestDodawaniaUzytkownikow extends ColumnFixture {
    String[] dane;

    public boolean dodajUzytkownika() {
        try {
            SetUp.uzytkownikRepository.addUzytkownik(new Uzytkownik(dane[0],dane[1],dane[2],dane[3],dane[4]));
            return true;
        } catch (UzytkownikRepositoryException ignored) {
            return false;
        }
    }
    public int liczbaUzytkownikow(){
        return SetUp.uzytkownikRepository.sizeUzytkownicy();
    }

    public boolean sprawdzUzytkownika(){
        Uzytkownik uzytkownik = SetUp.uzytkownikRepository.znajdzUzytkownika(dane[0]);
        return uzytkownik.getNazwa().equals(dane[0]) &&
                uzytkownik.getHaslo().equals(dane[1]) &&
                uzytkownik.getImie().equals(dane[2]) &&
                uzytkownik.getNazwisko().equals(dane[3]) &&
                uzytkownik.getRola().equals(dane[4]);
    }
}
