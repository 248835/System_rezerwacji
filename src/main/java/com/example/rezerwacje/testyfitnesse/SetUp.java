package com.example.rezerwacje.testyfitnesse;

import com.example.rezerwacje.data.ObjectUzytkownikRepository;
import fit.Fixture;

public class SetUp extends Fixture {
    static ObjectUzytkownikRepository uzytkownikRepository;
    public SetUp(){
        uzytkownikRepository = new ObjectUzytkownikRepository();
    }
}
