package com.example.rezerwacje.web;

import com.example.rezerwacje.data.*;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/kierownik")
public class KierownikKontroler {

    private final UzytkownikRepository uzytkownikRepository;
    private final HotelRepository hotelRepository;
    private final PokojRepository pokojRepository;
    private final RezerwacjaRepository rezerwacjaRepository;

    @Autowired
    public KierownikKontroler(UzytkownikRepository uzytkownikRepository, HotelRepository hotelRepository,
                              PokojRepository pokojRepository, RezerwacjaRepository rezerwacjaRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.hotelRepository = hotelRepository;
        this.pokojRepository = pokojRepository;
        this.rezerwacjaRepository = rezerwacjaRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String widok(Model model) {
        Hotel hotel = hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa());
        if (hotel == null){
            model.addAttribute(new HotelForm());
            return "dodajHotel";
        }
        List<Rezerwacja> rezerwacje = rezerwacjaRepository.znajdzRezerwacjeHotelu(getKierownik().getNazwa());
        for (Rezerwacja rezerwacja : rezerwacje) {
            rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
            rezerwacja.setUzytkownik(uzytkownikRepository.znajdzUzytkownika(rezerwacja.getUzytkownik().getNazwa()));
        }
        model.addAttribute("rezerwacje", rezerwacje);
        model.addAttribute("hotel", hotel);
        model.addAttribute("pokoje", pokojRepository.znajdzPokojeNazwaKierownika(getKierownik().getNazwa()));
        model.addAttribute("pracownicy", uzytkownikRepository.znajdzPracownika(getKierownik().getNazwa()));

        return "widok";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String dodajHotel(@Valid HotelForm hotelForm, BindingResult errors){
        if (errors.hasErrors()){
            return "dodajHotel";
        }
        hotelRepository.dodajHotel(hotelForm.toHotel(), getKierownik().getNazwa());

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pokoj/{idPokoju}")
    public String modyfikujPokoj(@PathVariable int idPokoju, Model model) {
        model.addAttribute(new PokojForm(pokojRepository.znajdzPokoj(idPokoju)));

        return "pokoj";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pokoj/{idPokoju}")
    public String modyfikujPokoj(@PathVariable int idPokoju, @Valid PokojForm pokojForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return "pokoj";
        }
        try {
            pokojRepository.zmienDanePokoju(pokojForm, idPokoju);
        } catch (DuplicateKeyException e) {
            errors.rejectValue("numer", "{user.error}", "Pokój z danym numerym już istnieje");
        }
        if (errors.hasErrors()) {
            return "pokoj";
        }

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dodajPokoj")
    public String dodajPokoj(Model model) {
        model.addAttribute(new PokojForm());

        return "dodajPokoj";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dodajPokoj")
    public String dodajPokoj(@Valid PokojForm pokojForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return "dodajPokoj";
        }
        try {
            pokojRepository.dodajPokoj(pokojForm.toPokoj(),
                    hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()).getId());
        } catch (DuplicateKeyException e) {
            errors.rejectValue("numer", "{user.error}", "Pokój z danym numerym już istnieje");
        }
        if (errors.hasErrors()) {
            return "dodajPokoj";
        }

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pokoj/{idPokoju}/usunPokoj")
    public String usunPokoj(@PathVariable int idPokoju, Model model) {
        model.addAttribute("id", idPokoju);

        return "usunPokoj";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pokoj/{idPokoju}/usunPokoj")
    public String usunPokoj(@PathVariable int idPokoju) {
        pokojRepository.usunPokoj(idPokoju);

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pracownik/{nazwaPracownika}")
    public String pracownik(@PathVariable String nazwaPracownika, Model model) {
        model.addAttribute(new ImieNazwiskoForm(uzytkownikRepository.znajdzUzytkownika(nazwaPracownika)));
        model.addAttribute("nazwa", nazwaPracownika);
        return "pracownik";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pracownik/{nazwaPracownika}")
    public String pracownik(@PathVariable String nazwaPracownika, ImieNazwiskoForm imieNazwiskoForm,
                            BindingResult errors) {
        if (errors.hasErrors()) {
            return "pracownik";
        }

        uzytkownikRepository.zmienImieNazwisko(imieNazwiskoForm, nazwaPracownika);

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pracownik/{nazwaPracownika}/usunPracownika")
    public String usunPracownika(@PathVariable String nazwaPracownika, Model model) {
        model.addAttribute("nazwa", nazwaPracownika);

        return "usunPracownika";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pracownik/{nazwaPracownika}/usunPracownika")
    public String usunPracownika(@PathVariable String nazwaPracownika) {
        uzytkownikRepository.usunKonto(nazwaPracownika);

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dodajPracownika")
    public String dodajPracownika(Model model) {
        model.addAttribute(new ImieNazwiskoForm());

        return "dodajPracownika";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dodajPracownika")
    public String dodajPracownika(ImieNazwiskoForm imieNazwiskoForm, BindingResult errors) {
        if (errors.hasErrors()){
            return "dodajPracownka";
        }
        Uzytkownik pracownik = new Uzytkownik(imieNazwiskoForm.getImie() + ' ' + imieNazwiskoForm.getNazwisko(),
                "password", imieNazwiskoForm.getImie(), imieNazwiskoForm.getNazwisko(),"ROLE_PRACOWNIK",
                getKierownik().getNazwa());

        uzytkownikRepository.addUzytkownik(pracownik);

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/modyfikujHotel")
    public String modyfikujHotel(Model model) {
        model.addAttribute(new HotelForm(hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa())));
        System.out.println(hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));

        return "modyfikujHotel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modyfikujHotel")
    public String modyfikujHotel(HotelForm hotelForm, BindingResult errors) {
        if (errors.hasErrors()){
            return "modyfikujHotel";
        }
        System.out.println(hotelForm);
        hotelRepository.zmienHotel(hotelForm,getKierownik().getNazwa());

        return "redirect:/kierownik";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usunHotel")
    public String usunHotel() {

        return "usunHotel";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usunHotel")
    public String usunHotelProcess() {
        hotelRepository.usunHotel(getKierownik().getNazwa());

        return "redirect:/kierownik";
    }

    private Uzytkownik getKierownik() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return uzytkownikRepository.znajdzUzytkownika(username);
    }
}
