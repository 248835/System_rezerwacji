package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.*;

public class ObjectRezerwacjaRepository implements RezerwacjaRepository{
    private static ObjectRezerwacjaRepository rezerwacja_instance = null;
    private final Map<Uzytkownik,List<Rezerwacja>> rezerwacje = new HashMap<>();

    public List<Rezerwacja> znajdzRezerwacje(Hotel hotel){
        List<Rezerwacja> rezerwacjeHotelu = new ArrayList<>();
        for (List<Rezerwacja> rezerwacjaList : rezerwacje.values()){
            for (Rezerwacja rezerwacja : rezerwacjaList)
                if (rezerwacja.getHotel().equals(hotel))
                    rezerwacjeHotelu.add(rezerwacja);
        }
        return rezerwacjeHotelu;
    }

    //pobierz liste rezerwacji przy zmianie danych uzytkownika
    List<Rezerwacja> znajdzRezerwacje(Uzytkownik uzytkownik){
        return rezerwacje.get(uzytkownik);
    }

    @Override
    public List<Rezerwacja> znajdzRezerwacjePracownik(Uzytkownik pracownik) {
        return znajdzRezerwacje(ObjectHotelRepository.getInstance()
                .znajdzHotelPracownika(pracownik));
    }

    public Rezerwacja usunRezerwacje(Rezerwacja rezerwacja){
        for (Uzytkownik uzytkownik : rezerwacje.keySet()){
            rezerwacje.get(uzytkownik).removeIf(rezerwacja1 -> rezerwacja1.equals(rezerwacja));
        }
        return rezerwacja;
    }

    @Override
    public void dodajRezerwacje(Rezerwacja rezerwacja, Uzytkownik uzytkownik) {
        if (!rezerwacje.containsKey(uzytkownik)){
            rezerwacje.put(uzytkownik,new ArrayList<>());
        }
        rezerwacje.get(uzytkownik).add(rezerwacja);
    }

    @Override
    public void modyfikujRezerwacje(Rezerwacja rezerwacja, Uzytkownik uzytkownik) {
        Rezerwacja rezerwacjaOld=null;
        for (Rezerwacja rezerwacja1 : rezerwacje.get(uzytkownik)){
            if (rezerwacja1.getHotel().equals(rezerwacja.getHotel())){
                rezerwacjaOld=rezerwacja1;
            }
        }
        rezerwacje.get(uzytkownik).remove(rezerwacjaOld);
        rezerwacje.get(uzytkownik).add(rezerwacja);
    }

    // usuawanie rezerwacji przy usunieciu uzytkownika
    void usunUzytkownika(Uzytkownik uzytkownik){
        rezerwacje.remove(uzytkownik);
    }

    // dodawanie rzerwacje po modyfikacji uzytkownika
    void dodajUzytkownika(Uzytkownik uzytkownik, List<Rezerwacja> rezerwacjeNew){
        rezerwacje.put(uzytkownik, rezerwacjeNew);
    }

    // usuwanie hotel
    void usunHotel(Hotel hotel){
        for (List<Rezerwacja> rezerwacje1 : rezerwacje.values()){
            rezerwacje1.removeIf(rezerwacja -> rezerwacja.getHotel().equals(hotel));
        }
    }

    // usuwanie pokoi
    void usunPokoj(Pokoj pokoj){
        for (List<Rezerwacja> rezerwacje1 : rezerwacje.values()){
            rezerwacje1.removeIf(rezerwacja -> rezerwacja.getPokoj().equals(pokoj));
        }
    }

    // modyfikowanie hoteli
    Map<Uzytkownik,List<Rezerwacja>> modyfikujHotel(Hotel hotel){
        Map<Uzytkownik,List<Rezerwacja>> rezerwacjOut = new HashMap<>();

        for (Uzytkownik uzytkownik : rezerwacje.keySet()){
            for (Rezerwacja rezerwacja : rezerwacje.get(uzytkownik)){
                if (rezerwacja.getHotel().equals(hotel)){
                    if (!rezerwacjOut.containsKey(uzytkownik)){
                        rezerwacjOut.put(uzytkownik,new ArrayList<>());
                    }
                    rezerwacjOut.get(uzytkownik).add(rezerwacja);
                }
            }
        }

        return rezerwacjOut;
    }

    // modyfikowanie pokoj
    Map<Uzytkownik,List<Rezerwacja>> modyfikujPokoj(Pokoj pokoj){
        Map<Uzytkownik,List<Rezerwacja>> rezerwacjOut = new HashMap<>();

        for (Uzytkownik uzytkownik : rezerwacje.keySet()){
            for (Rezerwacja rezerwacja : rezerwacje.get(uzytkownik)){
                if (rezerwacja.getPokoj().equals(pokoj)){
                    if (!rezerwacjOut.containsKey(uzytkownik)){
                        rezerwacjOut.put(uzytkownik,new ArrayList<>());
                    }
                    rezerwacjOut.get(uzytkownik).add(rezerwacja);
                }
            }
        }

        return rezerwacjOut;
    }

    public static ObjectRezerwacjaRepository getInstance() {
        if (rezerwacja_instance == null){
            rezerwacja_instance = new ObjectRezerwacjaRepository();
        }
        return rezerwacja_instance;
    }
}
