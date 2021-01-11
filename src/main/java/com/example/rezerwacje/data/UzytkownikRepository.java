package com.example.rezerwacje.data;

import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.HasloForm;
import com.example.rezerwacje.web.forms.ImieNazwiskoForm;

public interface UzytkownikRepository{
    Uzytkownik znajdzUzytkownika(String nazwa);

    void addUzytkownik(Uzytkownik uzytkownik);

    void zmienImieNazwisko(ImieNazwiskoForm imieNazwiskoForm, String nazwa);

    void zmienHaslo(HasloForm hasloForm, String nazwa);

    void usunKonto(String nazwa);
    
	List<Uzytkownik> znajdzPracownika(String nazwaKierwonika);
}
