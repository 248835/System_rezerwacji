package com.example.rezerwacje.web;

import com.example.rezerwacje.data.*;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/kierownik")
public class KierownikKontroler {

    private final UzytkownikRepository uzytkownikRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public KierownikKontroler(UzytkownikRepository uzytkownikRepository, HotelRepository hotelRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
        this.hotelRepository = hotelRepository;
    }

    // todo analogicznie do widoku pracownika chyba najlepiej będzie zrobić 3 listy - pokoi, pracowników i rezerwacji
    // todo kliknięcie elementu listy -> przejście do strony z opcjami
    // todo na górze mogą być dane hotelu z przyciskami do edycji danych lub usunięcia hotelu
    // todo ale to jak uważasz, jest pierwsza i ja przestaję myśleć
    @RequestMapping(method = RequestMethod.GET)
    public String hotel(Model model){
        model.addAttribute("hotel",hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
        return "hotelKierownika";
    }

    private Uzytkownik getKierownik(){
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
