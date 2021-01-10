package com.example.rezerwacje.web;

import com.example.rezerwacje.data.HotelRepository;
import com.example.rezerwacje.data.PokojRepository;
import com.example.rezerwacje.data.RezerwacjaRepository;
import com.example.rezerwacje.data.UzytkownikRepository;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.MiastoForm;
import com.example.rezerwacje.web.forms.RezerwacjaForm;
import com.example.rezerwacje.web.forms.UzytkownikForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/")
public class HomeKontroler {
    //tak dodajesz repozytoria do kontrolera
    private final HotelRepository hotelRepository;
    private final PokojRepository pokojRepository;
    private final RezerwacjaRepository rezerwacjaRepository;
    private final UzytkownikRepository uzytkownikRepository;

    @Autowired
    public HomeKontroler(HotelRepository hotelRepository, PokojRepository pokojRepository, RezerwacjaRepository rezerwacjaRepository, UzytkownikRepository uzytkownikRepository) {
        this.hotelRepository = hotelRepository;
        this.pokojRepository = pokojRepository;
        this.rezerwacjaRepository = rezerwacjaRepository;
        this.uzytkownikRepository = uzytkownikRepository;
    }

    // GET - kiedy nie zmieniasz nic na serwerze
    @RequestMapping(method = GET)
    public String home(Model model) {
        model.addAttribute(new MiastoForm());
        return "home";
    }

    // POST - kiedy zmieniasz cos na serwerze
    // ta metoda jest do dupy i się nią nie wzoruj, w najbliższym czasie ją zmienię. Poprawna tego typu metoda to rejestracja()
    @RequestMapping(method = POST)
    public String processHome(
            RedirectAttributes redirectAttributes,
            MiastoForm miastoForm) {

        redirectAttributes.addAttribute("miasto", miastoForm.getMiasto());

        return "redirect:/{miasto}";
    }

    @RequestMapping(value = "/{miasto}", method = GET)
    public String hotele(
            @PathVariable String miasto,
            Model model) {

        model.addAttribute("hotelList", hotelRepository.znajdzHoteleMiasto(miasto));

        return "hotele";
    }

    @RequestMapping(value = "/{miasto}/{nazwa}", method = GET)
    public String hotel(@PathVariable String miasto, @PathVariable String nazwa, Model model) {
        Hotel hotel = hotelRepository.znajdzHotel(miasto, nazwa);
        model.addAttribute("hotel", hotel);
        model.addAttribute("pokoje", pokojRepository.znajdzPokoje(hotel));
        return "hotel";
    }

    //todo ogarnąć rozdrobnienie rezerwacji w ciągłe przedziały
    @RequestMapping(value = "/{miasto}/{nazwa}/{id}", method = GET)
    public String pokoj(@PathVariable String miasto, @PathVariable String nazwa, @PathVariable int id, Model model) {
        Hotel hotel = hotelRepository.znajdzHotel(miasto, nazwa);
        model.addAttribute("hotel", hotel);
        Pokoj pokoj = pokojRepository.znajdzPokoj(id);
        model.addAttribute("pokoj", pokoj);
        List<String> rezerwacje = new ArrayList<>();
        for (Date[] daty : rezerwacjaRepository.znajdzTerminyRezerwacji(pokoj)) {
            rezerwacje.add("Od " + daty[0] + " do " + daty[1]);
        }
        model.addAttribute("rezerwacje", rezerwacje);
        model.addAttribute(new RezerwacjaForm());

        return "pokoj";
    }

    //todo sprawdzanie dat
    @RequestMapping(value = "/{miasto}/{nazwa}/{id}", method = POST)
    public String rezerwacja(@PathVariable String miasto, @PathVariable String nazwa, @PathVariable int id,
                             RedirectAttributes redirectAttributes, RezerwacjaForm rezerwacjaForm) {
        rezerwacjaForm.setHotel(hotelRepository.znajdzHotel(miasto, nazwa));
        rezerwacjaForm.setPokoj(pokojRepository.znajdzPokoj(id));
        Rezerwacja rezerwacja = new Rezerwacja(rezerwacjaForm, getUzytkownik());
        System.out.println(rezerwacja.getUzytkownik().getNazwa());
        rezerwacjaRepository.dodajRezerwacje(rezerwacja);
        rezerwacja.setId(rezerwacjaRepository.znajdzIdRezerwacji(rezerwacja));

        redirectAttributes.addAttribute("rezerwacjaId", rezerwacja.getId());

        return "redirect:/{miasto}/{nazwa}/{id}/{rezerwacjaId}";
    }

    @RequestMapping(value = "/{miasto}/{nazwa}/{id}/{rezerwacjaId}", method = GET)
    public String rezerwacja(@PathVariable String miasto, @PathVariable String nazwa, @PathVariable int rezerwacjaId,
                             Model model) {
        Rezerwacja rezerwacja = rezerwacjaRepository.znajdzRezerwacje(rezerwacjaId);
        rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
        rezerwacja.setHotel(hotelRepository.znajdzHotel(miasto, nazwa));

        long diff = TimeUnit.DAYS.convert(Math.abs(
                rezerwacja.getKoniecRezerwacji().getTime() - rezerwacja.getPoczatekRezerwacji().getTime()
                ),
                TimeUnit.MILLISECONDS);

        model.addAttribute("rezerwacja",rezerwacja);
        model.addAttribute("cena",diff * rezerwacja.getPokoj().getCena());

        return "rezerwacja";
    }

    @RequestMapping(value = "/register", method = GET)
    public String rejestracja(Model model){
        model.addAttribute(new UzytkownikForm());
        return "register";
    }

    //todo bezpieczenstwo
    @RequestMapping(value = "/register", method = POST)
    public String rejestracja(UzytkownikForm uzytkownikForm){
        System.out.println(uzytkownikForm);
        uzytkownikRepository.addUzytkownik(uzytkownikForm.toUzytkownik());

        return "redirect:/login";
    }

    private Uzytkownik getUzytkownik(){
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