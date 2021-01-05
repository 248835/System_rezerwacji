package com.example.rezerwacje.web;

import com.example.rezerwacje.data.*;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/kierownik")
public class KierownikKontroler {
//    @Autowired
//    public KierownikKontroler(ObjectUzytkownikRepository jdbcUzytkownikRepository, ObjectHotelRepository objectHotelRepository, ObjectPokojRepository jdbcPokojRepository, ObjectRezerwacjaRepository jdbcRezerwacjaRepository) {
//        this.jdbcUzytkownikRepository = jdbcUzytkownikRepository;
//        this.objectHotelRepository = objectHotelRepository;
//        this.jdbcPokojRepository = jdbcPokojRepository;
//        this.jdbcRezerwacjaRepository = jdbcRezerwacjaRepository;
//    }

//    @RequestMapping(method = RequestMethod.GET)
//    public List<Hotel> ekranGlownyHotele(){
//
//        return null;
//    }
//
//    @RequestMapping(method = RequestMethod.GET)
//    public List<Uzytkownik> pokazPracownikow(){
//
//        return null;
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    public String ekranGlownyWybor(){
//        // todo logika wyboru miedzy wyborem hotelu, dodaniem hotelu, dodaniem pracownika - zwraca odpowiedni widok
//        return null;
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/hotele")
//    public List<Rezerwacja> listaRezerwacji(Hotel hotel){
//
//        return null;
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/hotele")
//    public Set<Pokoj> listaPokoi(Hotel hotel){
//
//        //todo logika wyboru hotelu
//        return null;
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotel")
    public String wyborZHotele(){
        String string=null;
        //todo logika wyboru między: wybór rezerwacji, dodaj pokój
        return string;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hotel/submit")
    public String hotelForm(){
        return "hotelForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotel/submit")
    public String przetworzHotelForm(Hotel hotel){

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pokoj/submit")
    public String pokojForm(){
        return "pokojForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pokoj/submit")
    public String przetworzPokojForm(Pokoj pokoj, Hotel hotel){

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pracownik/submit")
    public String pracownikForm(){
        return "pracownikForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pracownik/submit")
    public String przetworzPracownikForm(Uzytkownik pracownik){

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pracownik/usun")
    public String usunPracownika(){
        return "usunPracownika";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pracownik/usun")
    public void usunPracownikProcess(Uzytkownik pracownik){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pracownik/modyfikuj")
    public String modyfikujPracownik(Uzytkownik pracownik){
        return "modyfikujPracownika";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pracownik/modyfikuj")
    public void modyfikujPracownikProcess(Uzytkownik pracownik){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hotel/modyfikuj")
    public String modyfikujHotel(Hotel hotel){
        return "modyfikujHotel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotel/modyfikuj")
    public void modyfikujHotelProcess(Hotel hotel){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hotel/usun")
    public String usunHotel(Hotel hotel){
        return "usunHotel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/hotel/usun")
    public void usunHotelProcess(Hotel hotel){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pokoj/modyfikuj")
    public String modyfikujPokoj(Pokoj pokoj){
        return "modyfikujPokoj";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pokoj/modyfikuj")
    public void modyfikujPokojProcess(Pokoj pokoj){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pokoj/usun")
    public String usunPokoj(Pokoj pokoj){
        return "usunPokoj";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pokoj/usun")
    public void usunPokojProcess(Pokoj pokoj){
    }

    private Uzytkownik getKierownik(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }

        return null;
    }
}
