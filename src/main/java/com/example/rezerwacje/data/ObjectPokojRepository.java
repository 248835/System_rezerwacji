package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.*;

public class ObjectPokojRepository implements PokojRepository{
    private static ObjectPokojRepository pokoj_instance = null; //todo zmienic na bean

    private final Map<Pokoj, Hotel> pokoje = new HashMap<>();
    private static int id = 0;

    public Set<Pokoj> znajdzPokoje(Uzytkownik kierownik, Hotel hotel){
        Set<Pokoj> pokoje1 = new HashSet<>();

        for (Pokoj pokoj : pokoje.keySet()){
            if (pokoje.get(pokoj).equals(hotel))
                pokoje1.add(pokoj);
        }

        return pokoje1;
    }

    //fixme
    @Override
    public Map<Hotel, List<Pokoj>> znajdzOferty(String miasto, Date poczatek, Date koniec) {
        Map<Hotel, List<Pokoj>> oferty = new HashMap<>();
        for (Pokoj pokoj : pokoje.keySet()){
            Hotel hotel = pokoje.get(pokoj);
            if (hotel.getMiasto().equals(miasto)
                    /*&& !(pokoj.getPoczatekRezerwacji().compareTo(poczatek) <= 0 && pokoj.getKoniecRezerwacji().compareTo(koniec) >= 0)*/){
                if (!oferty.containsKey(hotel)) {
                    oferty.put(hotel, new ArrayList<>());
                }
                oferty.get(hotel).add(pokoj);
            }

        }

        return oferty;
    }

    @Override
    public void dodajPokoj(Pokoj pokoj, Hotel hotel) {
        pokoj.setId(id++);
        pokoje.put(pokoj, hotel);
    }

    @Override
    public void usunPokoj(Pokoj pokoj) {
        ObjectRezerwacjaRepository.getInstance().usunPokoj(pokoj);
        pokoje.remove(pokoj);
    }

    //todo ogarniecie rezerwacji
    @Override
    public void modyfikujPokoj(Pokoj pokoj) {
        Pokoj pokojOld=null;
        Hotel hotel=null;
        for (Pokoj pokoj1 : pokoje.keySet()){
            if (pokoj1.getId() == pokoj.getId()){
                pokojOld=pokoj1;
                hotel=pokoje.get(pokoj1);
            }
        }
        usunPokoj(pokojOld);
        dodajPokoj(pokoj,hotel);
    }

    // modyfikacja/usuwanie hotelu
    void usunHotel(Hotel hotel){
        pokoje.entrySet().removeIf(e -> e.getValue().equals(hotel));
    }

    //modyfikacja hotelu
    void dodajHotel(Hotel hotel, Set<Pokoj> pokoje1){
        for (Pokoj pokoj : pokoje1){
            pokoje.put(pokoj,hotel);
        }
    }

    public static ObjectPokojRepository getInstance() {
        if (pokoj_instance == null){
            pokoj_instance = new ObjectPokojRepository();
        }
        return pokoj_instance;
    }
}
