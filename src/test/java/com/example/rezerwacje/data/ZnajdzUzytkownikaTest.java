package com.example.rezerwacje.data;

import com.example.rezerwacje.Dane;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@Category(TestZnajdywanie.class)
public class ZnajdzUzytkownikaTest{
    private String string;
    private Uzytkownik uzytkownik;

    // @BeforeClass nie działa w przypadku wykorzystania @RunWith(Parameterized.class)
    static {
        System.out.println(">>> Inicjuj ObjectUzytkownikRepository");
        try {
            Dane.setObjectUzytkownikRepository();
        } catch (UzytkownikRepositoryException e) {
            e.printStackTrace();
        }
    }

    public ZnajdzUzytkownikaTest(String string, Uzytkownik uzytkownik) {
        this.string = string;
        this.uzytkownik = uzytkownik;
    }

    @Parameterized.Parameters
    public static Collection<Object> nazwyUzytkownikow(){
        List<Object> list = new ArrayList<>();
        for (Uzytkownik uzytkownik : Dane.getUzytkownicy()){
            list.add(new Object[]{uzytkownik.getNazwa(),uzytkownik});
        }
        list.add(new Object[]{"fake1",null});
        list.add(new Object[]{"fake2",null});
        return list;
    }

    @Test
    public void znajdzUzytkownikaTest(){
        System.out.println(">>> Znajdz uzytkownika");
        assertEquals(uzytkownik,ObjectUzytkownikRepository.getInstance().znajdzUzytkownika(string));
    }

    @AfterClass
    public static void clear(){
        System.out.println(">>> Czysc repozytorium uzytkownikow");
        ObjectUzytkownikRepository.getInstance().clearUzytkownicy();
    }
}