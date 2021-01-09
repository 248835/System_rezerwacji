package com.example.rezerwacje.data;

import com.example.rezerwacje.Dane;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

@Category(TestDodawanie.class)
public class DodajUzytkownikaTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void initObjectUzytkownikRepository() throws UzytkownikRepositoryException {
        System.out.println(">>> Inicjuj ObjectUzytkownikRepository");
        Dane.setObjectUzytkownikRepository();
    }

    @Test
    public void dodajUzytkownikaTest() throws UzytkownikRepositoryException {
        System.out.println(">>> Dodaj uzytkownika");
        Uzytkownik uzytkownik = new Uzytkownik("nowyUzytkownik","1234","Jan","Kowalski","KLIENT");

        ObjectUzytkownikRepository.getInstance().addUzytkownik(uzytkownik);
        assertEquals(uzytkownik,ObjectUzytkownikRepository.getInstance().znajdzUzytkownika(uzytkownik.getNazwa()));
    }

    @Test/*(expected = UzytkownikRepositoryException.class)*/
    public void dodajIstniejacegoUzytkownikaTest() throws UzytkownikRepositoryException {
        System.out.println(">>> Dodaj istniejacego uzytkownika");
        expectedException.expect(UzytkownikRepositoryException.class);
        expectedException.expectMessage("Proba dodania istniejacego uzytkownika");
        ObjectUzytkownikRepository.getInstance().addUzytkownik(Dane.getKlient().get(0));
    }

    @AfterClass
    public static void clear(){
        System.out.println(">>> Czysc repozytorium uzytkownikow");
        ObjectUzytkownikRepository.getInstance().clearUzytkownicy();
    }
}
