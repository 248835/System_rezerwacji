package com.example.rezerwacje.web;

import com.example.rezerwacje.data.HotelRepository;
import com.example.rezerwacje.data.PokojRepository;
import com.example.rezerwacje.data.RezerwacjaRepository;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.MiastoForm;
import com.example.rezerwacje.web.forms.RezerwacjaForm;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final HotelRepository hotelRepository;
    private final PokojRepository pokojRepository;
    private final RezerwacjaRepository rezerwacjaRepository;

    @Autowired
    public HomeKontroler(HotelRepository hotelRepository, PokojRepository pokojRepository, RezerwacjaRepository rezerwacjaRepository) {
        this.hotelRepository = hotelRepository;
        this.pokojRepository = pokojRepository;
        this.rezerwacjaRepository = rezerwacjaRepository;
    }

    // GET - kiedy nie zmieniasz nic na serwerze
    // POST - kiedy zmieniasz cos na serwerze
    @RequestMapping(method = GET)
    public String home(Model model) {
        model.addAttribute(new MiastoForm());
        return "home";
    }

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

        model.addAttribute("hotelList", hotelRepository.znajdzHotele(miasto));

        return "hotele";
    }

    @RequestMapping(value = "/{miasto}/{nazwa}", method = GET)
    public String hotel(@PathVariable String miasto, @PathVariable String nazwa, Model model) {
        Hotel hotel = hotelRepository.znajdzHotel(miasto, nazwa);
        model.addAttribute("hotel", hotel);
        model.addAttribute("pokoje", pokojRepository.znajdzPokoje(hotel));
        return "hotel";
    }

    //DateTimeFormat
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

    @RequestMapping(value = "/{miasto}/{nazwa}/{id}", method = POST)
    public String rezerwacja(@PathVariable String miasto, @PathVariable String nazwa, @PathVariable int id,
                             RedirectAttributes redirectAttributes, RezerwacjaForm rezerwacjaForm) {
        rezerwacjaForm.setHotel(hotelRepository.znajdzHotel(miasto, nazwa));
        rezerwacjaForm.setPokoj(pokojRepository.znajdzPokoj(id));
        //todo faktyczny uzytkownik
        Rezerwacja rezerwacja = new Rezerwacja(rezerwacjaForm, new Uzytkownik("dziwak"));
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
}