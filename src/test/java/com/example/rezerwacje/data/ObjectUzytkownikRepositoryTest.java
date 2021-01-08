package com.example.rezerwacje.data;

import com.example.rezerwacje.Dane;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ObjectUzytkownikRepositoryTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initObjectUzytkownikRepository() throws UzytkownikRepositoryException {
        System.out.println(">>> Initialize ObjectUzytkownikRepository");
        Dane.setObjectUzytkownikRepository();
    }

    @Test
    public void dodajUzytkownika() throws UzytkownikRepositoryException {
        System.out.println(">>> Dodaj uzytkownika");
        Uzytkownik uzytkownik = new Uzytkownik("nowyUzytkownik","1234","Jan","Kowalski","KLIENT");

        ObjectUzytkownikRepository.getInstance().addUzytkownik(uzytkownik);
        assertEquals(uzytkownik,ObjectUzytkownikRepository.getInstance().znajdzUzytkownika(uzytkownik.getNazwa()));
    }

    @Test/*(expected = UzytkownikRepositoryException.class)*/
    public void dodajIstniejacegoUzytkownika() throws UzytkownikRepositoryException {
        System.out.println(">>> Dodaj istniejacego uzytkownika");
        expectedException.expect(UzytkownikRepositoryException.class);
        expectedException.expectMessage("Proba dodania istniejacego uzytkownika");
        ObjectUzytkownikRepository.getInstance().addUzytkownik(Dane.getKlient().get(0));
    }
}