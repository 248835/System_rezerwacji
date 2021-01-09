package com.example.rezerwacje.data;

import com.example.rezerwacje.Dane;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertNull;

@Category(TestUsuwanie.class)
public class UsunUzytkownikaTest {
    @BeforeClass
    public static void initObjectUzytkownikRepository() throws UzytkownikRepositoryException {
        System.out.println(">>> Inicjuj ObjectUzytkownikRepository");
        Dane.setObjectUzytkownikRepository();
    }

    @Test
    public void usunUzytkownikaTest(){
        System.out.println(">>> Usun uzytkownika");
        ObjectUzytkownikRepository.getInstance().usunUzytkownika(Dane.getKlient().get(0));
        assertNull(ObjectUzytkownikRepository.getInstance().znajdzUzytkownika(Dane.getKlient().get(0).getNazwa()));
    }

    @AfterClass
    public static void clear(){
        System.out.println(">>> Czysc repozytorium uzytkownikow");
        ObjectUzytkownikRepository.getInstance().clearUzytkownicy();
    }
}
