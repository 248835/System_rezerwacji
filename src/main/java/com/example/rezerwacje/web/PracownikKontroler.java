package com.example.rezerwacje.web;

import com.example.rezerwacje.data.HotelRepository;
import com.example.rezerwacje.data.PokojRepository;
import com.example.rezerwacje.data.RezerwacjaRepository;
import com.example.rezerwacje.data.UzytkownikRepository;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/pracownik")
public class PracownikKontroler {
    private final UzytkownikRepository uzytkownikRepository;
    private final RezerwacjaRepository rezerwacjaRepository;
    private final HotelRepository hotelRepository;
    private final PokojRepository pokojRepository;

    public PracownikKontroler(UzytkownikRepository uzytkownikRepository, RezerwacjaRepository rezerwacjaRepository, HotelRepository hotelRepository, PokojRepository pokojRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.rezerwacjaRepository = rezerwacjaRepository;
        this.hotelRepository = hotelRepository;
        this.pokojRepository = pokojRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String rezerwacje(Model model){
        String kierownik = getPracownik().getNazwaKierownika();
        List<Rezerwacja> rezerwacje = rezerwacjaRepository.znajdzRezerwacjeHotelu(kierownik);
//        for (Rezerwacja rezerwacja : rezerwacje){
//            rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
//            rezerwacja.setUzytkownik(uzytkownikRepository.znajdzUzytkownika(rezerwacja.getUzytkownik().getNazwa()));
//        }
        model.addAttribute("rezerwacje",rezerwacje);
        model.addAttribute("hotel",hotelRepository.znajdzHotelKierownik(kierownik));
        model.addAttribute("pokoje",pokojRepository.znajdzPokojeNazwaKierownika(kierownik));
        model.addAttribute("kierownik",kierownik);

        return "widok";
    }

    @RequestMapping(value = "/{idRezerwacji}", method = RequestMethod.GET)
    public String zaplac(Model model, @PathVariable int idRezerwacji){
        Rezerwacja rezerwacja = rezerwacjaRepository.znajdzRezerwacje(idRezerwacji);
        rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
        if (getPracownik().getRola().equals("ROLE_PRACOWNIK"))
            rezerwacja.setHotel(hotelRepository.znajdzHotelKierownik(getPracownik().getNazwaKierownika()));
        else
            rezerwacja.setHotel(hotelRepository.znajdzHotelKierownik(getPracownik().getNazwa()));

        long diff = TimeUnit.DAYS.convert(Math.abs(
                rezerwacja.getKoniecRezerwacji().getTime() - rezerwacja.getPoczatekRezerwacji().getTime()
                ),
                TimeUnit.MILLISECONDS);

        model.addAttribute("rezerwacja",rezerwacja);
        model.addAttribute("cena",diff * rezerwacja.getPokoj().getCena());
        return "rachunek";
    }

    @RequestMapping(value = "/{idRezerwacji}", method = RequestMethod.POST)
    public String zaplac(@PathVariable int idRezerwacji){
        rezerwacjaRepository.usunRezerwacje(idRezerwacji);
        if (getPracownik().getRola().equals("ROLE_PRACOWNIK"))
            return "redirect:/pracownik";
        return "redirect:/kierownik";
    }

    private Uzytkownik getPracownik(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if(principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }

        return uzytkownikRepository.znajdzUzytkownika(username);
    }
}
