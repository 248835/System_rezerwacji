package com.example.rezerwacje.web;

import com.example.rezerwacje.data.*;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.rezerwacja.Oferta;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/uzytkownik")
public class KlientKontroler {
//    @Autowired
//    public KlientKontroler(ObjectHotelRepository hotelRepository, ObjectRezerwacjaRepository jdbcRezerwacjaRepository) {   //todo
//        this.hotelRepository = hotelRepository;
//        this.jdbcRezerwacjaRepository = jdbcRezerwacjaRepository;
//    }

    //https://stackoverflow.com/questions/23144358/how-to-loop-through-map-in-thymeleaf
    @RequestMapping(method = RequestMethod.GET)
    public Map<Hotel,List<Pokoj>> pokazOferty(String adres, Date poczatek, Date koniec){
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String wybierzZOfert(RedirectAttributes redirectAttributes,
                              Oferta oferta){
        redirectAttributes.addAttribute("nazwaHotelu",oferta.getHotel().getNazwa());
        redirectAttributes.addFlashAttribute("oferta",oferta);
        return "redirect:/oferty/{nazwaHotelu}";
    }

    //https://www.baeldung.com/spring-web-flash-attributes
    @RequestMapping(method = RequestMethod.GET, value = "/oferty/{nazwaHotelu}")
    public String pokazOferte(){
        return "oferta";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oferty/{nazwaHotelu}")
    public String wybierzOferte(Rezerwacja rezerwacja){
        return "redirect:";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usunKonto")
    public String usunKonto(){
        return "usunKontoUzytkownika";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/usunKonto")
    public void usunKontoProcess(){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/modyfikujKonto")
    public Uzytkownik modyfikujKonto(){
        return getUzytkownik();
    }

    @RequestMapping(method = RequestMethod.POST,value = "/modyfikujKonto")
    public void modyfikujKontoProcess(Uzytkownik uzytkownik){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/modyfikujRezerwacje")
    public String modyfikujRezerwacje(Rezerwacja rezerwacja){
        return "modyfikujRezerwacje";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/modyfikujRezerwacje")
    public void modyfikujRezerwacjeProcess(Rezerwacja rezerwacja){
    }

    @RequestMapping(method = RequestMethod.GET, value = "/usunRezerwacje")
    public String usunRezerwacje(Rezerwacja rezerwacja){
        return "usunRezerwacje";
    }

    @RequestMapping(method = RequestMethod.POST,value = "/usumRezerwacje")
    public void usunRezerwacjeProcess(Rezerwacja rezerwacja){
    }

    private Uzytkownik getUzytkownik(){
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
