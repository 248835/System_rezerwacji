package com.example.rezerwacje.web;

import com.example.rezerwacje.data.UzytkownikRepository;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/klient")
public class KlientKontroler {
    private final UzytkownikRepository uzytkownikRepository;

    @Autowired
    public KlientKontroler(UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
    }

    //todo dla pracownika wyświetlać imię i nazwisko kierownika
    @RequestMapping(method = RequestMethod.GET)
    public String pokazOferty(Model model){
        model.addAttribute(getUzytkownik());
        return "konto";
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
