package com.example.rezerwacje.testyfitnesse;

import com.example.rezerwacje.data.*;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;

import java.util.*;

public class Dane {

    private static final List<Uzytkownik> uzytkownicy = new ArrayList<Uzytkownik>(Arrays.asList(
            new Uzytkownik("uzytkownik1","haslo1","imie1","nazwisko1","KLIENT"),
            new Uzytkownik("uzytkownik3","haslo3","imie3","nazwisko3","KIEROWNIK"),
            new Uzytkownik("uzytkownik8","haslo8","imie8","nazwisko8","KIEROWNIK"),
            new Uzytkownik("uzytkownik2","haslo2","imie2","nazwisko2","PRACOWNIK","uzytkownik3"),
            new Uzytkownik("uzytkownik4","haslo4","imie4","nazwisko4","KLIENT"),
            new Uzytkownik("uzytkownik5","haslo5","imie5","nazwisko5","KLIENT"),
            new Uzytkownik("uzytkownik6","haslo6","imie6","nazwisko6","PRACOWNIK","uzytkownik3"),
            new Uzytkownik("uzytkownik9","haslo9","imie9","nazwisko9","PRACOWNIK","uzytkownik8"),
            new Uzytkownik("uzytkownik7","haslo7","imie7","nazwisko7","KLIENT")
            ));

    public static List<Uzytkownik> getUzytkownicy() {
        return uzytkownicy;
    }

    public static List<Uzytkownik> getKierownik(){
        return new ArrayList<>(
                Arrays.asList(uzytkownicy.get(2), uzytkownicy.get(3))
        );
    }

    public static List<Uzytkownik> getPracownik(){
        return new ArrayList<>(
                Arrays.asList(uzytkownicy.get(1),uzytkownicy.get(6),uzytkownicy.get(7))
        );
    }

    public static List<Uzytkownik> getKlient(){
        return new ArrayList<>(
                Arrays.asList(uzytkownicy.get(0),uzytkownicy.get(4),uzytkownicy.get(5),uzytkownicy.get(8))
        );
    }

    private static Uzytkownik getKierownik(Uzytkownik pracownik){
        for (Uzytkownik kierownik : uzytkownicy){
            if (kierownik.getNazwa().equals(pracownik.getNazwaKierownika()))
                return kierownik;
        }
        return null;
    }

    public static void setObjectUzytkownikRepository() throws UzytkownikRepositoryException {
        for (Uzytkownik uzytkownik : uzytkownicy){
            if (uzytkownik.getRola().equals("PRACOWNIK")){
                ObjectUzytkownikRepository.getInstance().addPracownik(uzytkownik,getKierownik(uzytkownik));
            }else{
                ObjectUzytkownikRepository.getInstance().addUzytkownik(uzytkownik);
            }
        }
    }

    private static final List<Hotel> hotele = new ArrayList<Hotel>(Arrays.asList(
            new Hotel("nazwa1","miasto1","ulica1",1),   //kierownik1
            new Hotel("nazwa2","miasto2","ulica2",2),   //kierownik2
            new Hotel("nazwa3","miasto3","ulica3",3)    //kierownik1
    ));

    public static List<Hotel> getHotele() {
        return hotele;
    }

    public static void setObjectHotelRepository() {
        int i = 0;
        for (Hotel hotel : hotele){
            ObjectHotelRepository.getInstance().addHotel(getKierownik().get(i++%2),hotel);
        }
    }

    private static final List<Pokoj> pokoje = new ArrayList<Pokoj>(Arrays.asList(
            new Pokoj(1,1,1,1,1),   //hotel1
            new Pokoj(2,2,2,2,2),   //hotel2
            new Pokoj(3,3,3,3,3),   //hotel3
            new Pokoj(4,4,4,4,4),   //hotel1
            new Pokoj(5,5,5,5,5),   //hotel2
            new Pokoj(6,6,6,6,6),   //hotel3
            new Pokoj(7,7,7,7,7),   //hotel1
            new Pokoj(8,8,8,8,8)    //hotel2
    ));

    public static List<Pokoj> getPokoje() {
        return pokoje;
    }

    public static void setObjectPokojRepository() {
        int i = 0 ;
        for (Pokoj pokoj : pokoje){
            ObjectPokojRepository.getInstance().dodajPokoj(pokoj, hotele.get(i++%3));
        }
    }

    private static final List<Rezerwacja> rezerwacje = new ArrayList<>(Arrays.asList(
            new Rezerwacja(1, hotele.get(0), pokoje.get(0), getKlient().get(0),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(1), pokoje.get(1), getKlient().get(1),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(2), pokoje.get(2), getKlient().get(2),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(0), pokoje.get(0), getKlient().get(3),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(1), pokoje.get(1), getKlient().get(0),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(2), pokoje.get(2), getKlient().get(1),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(0), pokoje.get(0), getKlient().get(2),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(1), pokoje.get(1), getKlient().get(3),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2)),
            new Rezerwacja(1, hotele.get(2), pokoje.get(2), getKlient().get(0),new Date(2020,Calendar.JANUARY,1),new Date(2020,Calendar.JANUARY,2))
    ));

    public static List<Rezerwacja> getRezerwacje() {
        return rezerwacje;
    }

    public static void setObjectRezerwacjaRepository(){
        for (Rezerwacja rezerwacja : rezerwacje){
            ObjectRezerwacjaRepository.getInstance().dodajRezerwacje(rezerwacja,rezerwacja.getUzytkownik());
        }
    }
}
