package com.example.rezerwacje.web;

import com.example.rezerwacje.data.HotelRepository;
import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.web.forms.MiastoForm;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/")
public class HomeKontroler {

    private final HotelRepository hotelRepository;

    @Autowired
    public HomeKontroler(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
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
            MiastoForm miastoForm){

        redirectAttributes.addAttribute("miasto", miastoForm.getMiasto());
        redirectAttributes.addFlashAttribute("miastoForm",miastoForm);

        return "redirect:/{miasto}";
    }

    @RequestMapping(value = "/{miasto}", method = GET)
    public String hotele(
            @PathVariable String miasto, Model model){

        model.addAttribute("hotelList",hotelRepository.znajdzHotele(miasto));

        return "hotele";
    }
}