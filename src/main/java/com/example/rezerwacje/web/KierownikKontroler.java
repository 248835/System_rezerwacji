package com.example.rezerwacje.web;

import com.example.rezerwacje.*;
import com.example.rezerwacje.data.*;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

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

    // todo analogicznie do widoku pracownika chyba najlepiej będzie zrobić 3 listy - pokoi, pracowników i rezerwacji
    // todo kliknięcie elementu listy -> przejście do strony z opcjami
    // todo na górze mogą być dane hotelu z przyciskami do edycji danych lub usunięcia hotelu
    // todo ale to jak uważasz, jest pierwsza i ja przestaję myśleć
    @RequestMapping(method = RequestMethod.GET)
    public String hotel(Model model){
        model.addAttribute("hotel",hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
        return "hotelKierownika";
    }
    
    @RequestMapping(method = RequestMethod.GET)
	public String pokoje(Model model){
		model.addAttribute("pokoje",pokojRepository.znajdzPokojeNazwaKierownika(getKierownik().getNazwa()));
		return "pokojeHotelu";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String pracownicy(Model model){
		model.addAttribute("pracownicy",uzytkownikRepository.znajdzPracownika(getKierownik().getNazwa()));
		return "pracownicyKierownika";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String rezerwacje(Model model){
        List<Rezerwacja> rezerwacje = rezerwacjaRepository.znajdzRezerwacjeHotelu(getKierownik().getNazwa());
        for (Rezerwacja rezerwacja : rezerwacje){
            rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
            rezerwacja.setUzytkownik(uzytkownikRepository.znajdzUzytkownika(rezerwacja.getUzytkownik().getNazwa()));
        }
        model.addAttribute("rezerwacje",rezerwacje);
        model.addAttribute("hotel",hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
        model.addAttribute("pokoje",pokojRepository.znajdzPokojeNazwaKierownika(getKierownik().getNazwa()));

        return "rezerwacje";
    }
	
	@RequestMapping(value = "/przyjmijHotel", method = RequestMethod.POST)
	public String stworzHotel(RedirectAttributes redirectAttributes,
							  @Valid HotelForm hotelForm, BindingResult errors) {
		hotelRepository.dodajHotel(hotelForm.toHotel(), getKierownik().getNazwa());
		return "hotel";
	}
	
	@RequestMapping(value = "/zmienHotel", method = RequestMethod.POST)
	public String zmienHotel(RedirectAttributes redirectAttributes,
							@Valid HotelDaneForm hotelDaneForm, BindingResult errors) {) {
		if(errors.hasErrors()) {
			return "zmienHotel";
		}
		hotelRepository.zmienHotel(hotelDaneForm, hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
		return "hotel";
	}
	
	@RequestMethod(value = "/ZamknijHotel", method = RequestMethod.POST)
	public String usunHotel() {
		hotelRepository.usunHotel(hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
		return "redirect:/kierownik/PrzyjmijHotel";
	}
	
	@RequestMethod(value = "/zatrudnij", method = RequestMethod.POST)
	public String zatrudnijPracownika(RedirectAttributes redirectAttributes,
									  @Valid UzytkownikForm uzytkownikForm, BindingResult errors) {
		Uzytkownik pracownik = uzytkownikForm.toUzytkownik();
		pracownik.setRola("ROLE_PRACOWNIK");
		pracownicy.setNazwaKierownika(getKierownik().getNazwa());
		uzytkownikRepository.addUzytkownik(uzytkownik);
		return "zatrudnij";
	}
	
	@RequestMethod(value = "/zwolnij{login}", method = RequestMethod.POST)
	public String zwolnijPracownika(@PathVariable String login) {
		uzytkownikRepository.usunKonto(login);
		return "redirect:/kierownik";
	}
	
	@RequestMapping(value = "/PNowy", method = RequestMethod.POST)
	public String dodajPokoj(RedirectAttributes redirectAttributes,
									  @Valid PokojForm pokojForm, BindingResult errors) {) {
		pokojRepository.dodajPokoj(pokojForm.toPokoj(), hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));
		return "redirect:/kierownik";
	}
	
	@RequestMapping(value = "/P{idPokoju}/zmien", method = RequestMethod.POST)
	public String zmienPokoj(@PathVariable int idPokoju, RedirectAttributes redirectAttributes,
						     @Valid PokojDaneForm pokojDaneForm, BindingResult errors) {) {
		if (errors.hasErrors()) {
			return "zmien";
		}
		pokojRepository.zmienDanePokoju(pokojDaneForm, idPokoju);
		return "redirect:/klient";
	}
	
	@RequestMapping(value = "/PUsun{idPokoju}", method = RequestMethod.POST)
	public String usunPokoj(@PathVariable int idPokoju) {
		pokojRepository.usunPokoj(idPokoju);
		return "redirect:/kierownik";
	}
	
	//Nie wiem czy jest potrzebne ze Springiem? - zrobione aby kierownik nie tracił możliwości pracownika
	@RequestMapping(value = "/R{idRezerwacji}", method = RequestMethod.GET)
    public String zaplac(Model model, @PathVariable int idRezerwacji){
        Rezerwacja rezerwacja = rezerwacjaRepository.znajdzRezerwacje(idRezerwacji);
        rezerwacja.setPokoj(pokojRepository.znajdzPokoj(rezerwacja.getPokoj().getId()));
        rezerwacja.setHotel(hotelRepository.znajdzHotelKierownik(getKierownik().getNazwa()));

        long diff = TimeUnit.DAYS.convert(Math.abs(
                rezerwacja.getKoniecRezerwacji().getTime() - rezerwacja.getPoczatekRezerwacji().getTime()
                ),
                TimeUnit.MILLISECONDS);

        model.addAttribute("rezerwacja",rezerwacja);
        model.addAttribute("cena",diff * rezerwacja.getPokoj().getCena());
        return "rachunek";
    }

    @RequestMapping(value = "/R{idRezerwacji}", method = RequestMethod.POST)
    public String zaplac(@PathVariable int idRezerwacji){
        rezerwacjaRepository.usunRezerwacje(idRezerwacji);
        return "redirect:/kierownik";
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
