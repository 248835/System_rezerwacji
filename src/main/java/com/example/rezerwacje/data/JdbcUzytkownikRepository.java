package com.example.rezerwacje.data;

import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUzytkownikRepository implements UzytkownikRepository{
    private static final String ZNAJDZ_UZYTKOWNIKA = "select * from uzytkownicy where nazwa = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUzytkownikRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public Uzytkownik znajdzUzytkownika(String nazwa) {
        return jdbcTemplate.queryForObject(ZNAJDZ_UZYTKOWNIKA,this::mapRow,nazwa);
    }

    @Override
    public List<Uzytkownik> znajdzPracownikow(Uzytkownik id) {
        return null;
    }

    @Override
    public void addUzytkownik(Uzytkownik uzytkownik) {

    }

    @Override
    public void addPracownik(Uzytkownik pracownik, Uzytkownik kierownik) {

    }

    @Override
    public void usunUzytkownika(Uzytkownik uzytkownik) {

    }

    @Override
    public void usunPracownika(Uzytkownik pracownik, Uzytkownik kierownik) {

    }

    @Override
    public void modyfikujUzytkownik(Uzytkownik uzytkownik) {

    }

    @Override
    public void modyfikujPracownik(Uzytkownik pracownik, Uzytkownik kierownik) {

    }

    private Uzytkownik mapRow(ResultSet resultSet, int i) throws SQLException {
        String nazwa = resultSet.getString("nazwa");
        String haslo = resultSet.getString("haslo");
        String imie = resultSet.getString("imie");
        String nazwisko = resultSet.getString("nazwisko");
        String rola = resultSet.getString("rola");
        String nazwaKierownika = resultSet.getString("NAZWA_KIEROWNIKA");

        return new Uzytkownik(nazwa,haslo,imie,nazwisko,rola,nazwaKierownika);
    }
}
