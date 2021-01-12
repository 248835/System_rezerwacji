package com.example.rezerwacje.web;

import com.example.rezerwacje.data.UzytkownikRepository;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.HasloForm;
import com.example.rezerwacje.web.forms.ImieNazwiskoForm;
import com.example.rezerwacje.web.forms.UzytkownikForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/klient")
public class KlientKontroler {
    private final UzytkownikRepository uzytkownikRepository;

    @Autowired
    public KlientKontroler(UzytkownikRepository uzytkownikRepository) {
        this.uzytkownikRepository = uzytkownikRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String pokazOferty(Model model) {
        Uzytkownik uzytkownik = getUzytkownik();
        if (uzytkownik.getRola().equals("ROLE_PRACOWNIK")) {
            Uzytkownik kierownik = uzytkownikRepository.znajdzUzytkownika(uzytkownik.getNazwaKierownika());
            String imieNazwiskoKierownika = kierownik.getImie() + ' ' + kierownik.getNazwisko();
            model.addAttribute("imieNazwiskoKierownika", imieNazwiskoKierownika);
        }
        model.addAttribute(uzytkownik);
        return "konto";
    }

    @RequestMapping(value = "/zmienImieNazwisko", method = RequestMethod.GET)
    public String zmienImieNazwisko(Model model) {
        model.addAttribute(new ImieNazwiskoForm(getUzytkownik()));

        return "zmienImieNazwisko";
    }

    @RequestMapping(value = "/zmienImieNazwisko", method = RequestMethod.POST)
    public String zmienImieNazwisko(@Valid ImieNazwiskoForm imieNazwiskoForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return "zmienImieNazwisko";
        }
        uzytkownikRepository.zmienImieNazwisko(imieNazwiskoForm, getUzytkownik().getNazwa());
        return "redirect:/klient";
    }

    @RequestMapping(value = "/zmienHaslo", method = RequestMethod.GET)
    public String zmienHaslo(Model model) {
        model.addAttribute(new HasloForm());

        return "zmienHaslo";
    }

    @RequestMapping(value = "/zmienHaslo", method = RequestMethod.POST)
    public String zmienHaslo(@Valid HasloForm hasloForm, BindingResult errors) {
        if (errors.hasErrors()) {
            return "zmienHaslo";
        }
        uzytkownikRepository.zmienHaslo(hasloForm, getUzytkownik().getNazwa());
        return "redirect:/klient";
    }

    @RequestMapping(value = "/usunKonto", method = RequestMethod.GET)
    public String usunKonto(Model model) {

        return "usunKonto";
    }

    @RequestMapping(value = "/usunKonto", method = RequestMethod.POST)
    public String usunKonto() {
        uzytkownikRepository.usunKonto(getUzytkownik().getNazwa());
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                        .getRequest();
        new SecurityContextLogoutHandler().logout(request, null, null);
        return "redirect:/";
    }

    private Uzytkownik getUzytkownik() {
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
