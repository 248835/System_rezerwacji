package com.example.rezerwacje.data;

import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.ArrayList;
import java.util.List;

public class ObjectUzytkownikRepository implements UzytkownikRepository {
    private static ObjectUzytkownikRepository uzytkownik_instance;
    private final List<Uzytkownik> uzytkownicy = new ArrayList<>();

    public Uzytkownik znajdzUzytkownika(String nazwa){
        for (Uzytkownik uzytkownik : uzytkownicy)
            if (uzytkownik.getNazwa().equals(nazwa))
                return uzytkownik;
        return null;
    }

    public List<Uzytkownik> znajdzPracownikow(Uzytkownik kierownik){

        return KierownikTable.getInstance().getPracownicy(kierownik);
    }

    @Override
    public void addUzytkownik(Uzytkownik uzytkownik) throws UzytkownikRepositoryException {
        if (znajdzUzytkownika(uzytkownik.getNazwa())!=null){
            throw new UzytkownikRepositoryException("Proba dodania istniejacego uzytkownika");
        }
        if (uzytkownik.getRola().equals("KIEROWNIK"))
            KierownikTable.getInstance().addKierownik(uzytkownik);
        uzytkownicy.add(uzytkownik);
    }

    @Override
    public void addPracownik(Uzytkownik pracownik, Uzytkownik kierownik) {
        KierownikTable.getInstance().addPracownik(pracownik, kierownik);
        uzytkownicy.add(pracownik);
    }

    @Override
    public void usunUzytkownika(Uzytkownik uzytkownik) {
        ObjectRezerwacjaRepository.getInstance().usunUzytkownika(uzytkownik);
        uzytkownicy.remove(uzytkownik);
    }

    @Override
    public void usunPracownika(Uzytkownik pracownik, Uzytkownik kierownik) {
        KierownikTable.getInstance().usunPracownik(pracownik, kierownik);
        uzytkownicy.remove(pracownik);
    }

    @Override
    public void modyfikujUzytkownik(Uzytkownik uzytkownik) throws UzytkownikRepositoryException {
        usunUzytkownika(uzytkownik);
        List<Rezerwacja> rezerwacje = ObjectRezerwacjaRepository.getInstance().znajdzRezerwacje(uzytkownik);
        ObjectRezerwacjaRepository.getInstance().usunUzytkownika(uzytkownik);
        ObjectRezerwacjaRepository.getInstance().dodajUzytkownika(uzytkownik,rezerwacje);
        addUzytkownik(uzytkownik);
    }

    @Override
    public void modyfikujPracownik(Uzytkownik pracownik, Uzytkownik kierownik) {
        KierownikTable.getInstance().usunPracownik(pracownik, kierownik);
        KierownikTable.getInstance().addPracownik(pracownik, kierownik);
        usunPracownika(pracownik, kierownik);
        addPracownik(pracownik, kierownik);

    }

    public static ObjectUzytkownikRepository getInstance() {
        if (uzytkownik_instance == null){
            uzytkownik_instance = new ObjectUzytkownikRepository();
        }
        return uzytkownik_instance;
    }
}
