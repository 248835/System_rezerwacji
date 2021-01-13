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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
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
    public String home(Model model, final Principal principal) {
        if (null != principal) {
            if (getUzytkownik().getRola().equals("ROLE_PRACOWNIK"))
                return "redirect:/pracownik";
            if (getUzytkownik().getRola().equals("ROLE_KIEROWNIK"))
                return "redirect:/kierownik";
        }
        model.addAttribute(new MiastoForm());
        return "home";
    }

    // POST - kiedy zmieniasz cos na serwerze
    @RequestMapping(method = POST)
    public String processHome(
            RedirectAttributes redirectAttributes, @Valid MiastoForm miastoForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return "home";
        }
        if (hotelRepository.znajdzHoteleMiasto(miastoForm.getMiasto()).isEmpty()){
            errors.rejectValue("miasto","user.error","Nie ma tekiego miasta w bazie");
        }
        if (errors.hasErrors()) {
            return "home";
        }

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
        return "pokoje";
    }

    @RequestMapping(value = "/rezerwacja/{miasto}/{nazwa}/{id}", method = GET)
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

        return "rezerwacja";
    }

    //todo sprawdzanie dat
    @RequestMapping(value = "/rezerwacja/{miasto}/{nazwa}/{id}", method = POST)
    public String rezerwacja(@PathVariable String miasto, @PathVariable String nazwa, @PathVariable int id,
                             RedirectAttributes redirectAttributes, @Valid RezerwacjaForm rezerwacjaForm,
                             BindingResult errors, Model model) {
        Hotel hotel = hotelRepository.znajdzHotel(miasto, nazwa);
        Pokoj pokoj = pokojRepository.znajdzPokoj(id);
        rezerwacjaForm.setHotel(hotel);
        rezerwacjaForm.setPokoj(pokoj);

        if (errors.hasErrors()) {
            model.addAttribute("hotel", hotel);
            model.addAttribute("pokoj", pokoj);
            List<String> rezerwacje = new ArrayList<>();
            for (Date[] daty : rezerwacjaRepository.znajdzTerminyRezerwacji(pokoj)) {
                rezerwacje.add("Od " + daty[0] + " do " + daty[1]);
            }
            model.addAttribute("rezerwacje", rezerwacje);
            return "rezerwacja";
        }

        Rezerwacja rezerwacja = new Rezerwacja(rezerwacjaForm, getUzytkownik());
        try {
            rezerwacjaRepository.dodajRezerwacje(rezerwacja);
        }catch (DataIntegrityViolationException e){
            //todo sprawdzić na postgres
            errors.rejectValue("poczatekRezerwacji","error.user","Wprowadzono daty w złęj kolejności lub wybrano zajęty termin");
            errors.rejectValue("koniecRezerwacji","error.user","");
        }
        if (errors.hasErrors()) {
            model.addAttribute("hotel", hotel);
            model.addAttribute("pokoj", pokoj);
            List<String> rezerwacje = new ArrayList<>();
            for (Date[] daty : rezerwacjaRepository.znajdzTerminyRezerwacji(pokoj)) {
                rezerwacje.add("Od " + daty[0] + " do " + daty[1]);
            }
            model.addAttribute("rezerwacje", rezerwacje);
            return "rezerwacja";
        }

        rezerwacja.setId(rezerwacjaRepository.znajdzIdRezerwacji(rezerwacja));

        redirectAttributes.addAttribute("rezerwacjaId", rezerwacja.getId());

        return "redirect:/rezerwacja/{miasto}/{nazwa}/{id}/{rezerwacjaId}";
    }

    @RequestMapping(value = "/rezerwacja/{miasto}/{nazwa}/{id}/{rezerwacjaId}", method = GET)
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

        return "potwierdzenieRezerwacji";
    }

    @RequestMapping(value = "/register", method = GET)
    public String rejestracja(Model model){
        model.addAttribute(new UzytkownikForm());
        return "rejestracja";
    }

    @RequestMapping(value = "/register", method = POST)
    public String rejestracja(@Valid UzytkownikForm uzytkownikForm, BindingResult errors){
        if (errors.hasErrors()) {
            return "register";
        }
        try {
            uzytkownikRepository.addUzytkownik(uzytkownikForm.toUzytkownik());
        }catch(DuplicateKeyException e){
            errors.rejectValue("nazwa","user.error","Dana nazwa jest już zajęta");
        }
        if (errors.hasErrors()) {
            return "register";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "/rezerwacje",method = GET)
    public String rezerwacje(Model model){
        List<Rezerwacja> rezerwacje = rezerwacjaRepository.znajdRezerwacje(getUzytkownik().getNazwa());
        for (Rezerwacja rezerwacja : rezerwacje){
            rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
            rezerwacja.setHotel(hotelRepository.znajdzHotel(rezerwacja.getPokoj()));
        }
        model.addAttribute(rezerwacje);

        return "rezerwacje";
    }

    @RequestMapping(value = "/rezerwacje/usunRezerwacje/{idRezerwacji}", method = GET)
    public String usunRezerwacje(@PathVariable int idRezerwacji, Model model){
        model.addAttribute("id",idRezerwacji);

        return "usunRezerwacje";
    }

    @RequestMapping(value = "/rezerwacje/usunRezerwacje/{idRezerwacji}", method = POST)
    public String usunRezerwacje(@PathVariable int idRezerwacji){
        rezerwacjaRepository.usunRezerwacje(idRezerwacji);

        return "redirect:/rezerwacje";
    }

    @RequestMapping(value = "/rezerwacje/anulujRezerwacje/{id}",method = GET)
    public String anulujRezerwacje(@PathVariable int id){
        rezerwacjaRepository.usunRezerwacje(id);

        return "redirect:/";
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