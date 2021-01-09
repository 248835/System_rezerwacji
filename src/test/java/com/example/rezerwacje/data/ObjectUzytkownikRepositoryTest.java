package com.example.rezerwacje.data;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory({TestZnajdywanie.class,TestDodawanie.class,TestUsuwanie.class})
@Suite.SuiteClasses({ZnajdzUzytkownikaTest.class,DodajUzytkownikaTest.class,UsunUzytkownikaTest.class})
public class ObjectUzytkownikRepositoryTest {
}