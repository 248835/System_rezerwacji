package com.example.rezerwacje.data;

import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUzytkownikRepository implements UzytkownikRepository{
    private static final String SELECT_FROM_UZYTKOWNICY_WHERE = "select * from uzytkownicy where ";
    private static final String SELECT_FROM_UZYTKOWNICY_WHERE_NAZWA = SELECT_FROM_UZYTKOWNICY_WHERE + "nazwa = ?";
    private static final String INSERT_UZYTKOWNIK = "INSERT INTO UZYTKOWNICY(NAZWA, HASLO, IMIE, NAZWISKO, ROLA, NAZWA_KIEROWNIKA)\n" +
            "VALUES(?,?,?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUzytkownikRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public Uzytkownik znajdzUzytkownika(String nazwa) {
        return jdbcTemplate.queryForObject(SELECT_FROM_UZYTKOWNICY_WHERE_NAZWA,this::mapRow,nazwa);
    }

    @Override
    public void addUzytkownik(Uzytkownik uzytkownik) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        jdbcTemplate.update(INSERT_UZYTKOWNIK,
                uzytkownik.getNazwa(),
                encoder.encode(uzytkownik.getHaslo()),
                uzytkownik.getImie(),
                uzytkownik.getNazwisko(),
                uzytkownik.getRola(),
                uzytkownik.getNazwaKierownika());
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
