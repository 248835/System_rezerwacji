package com.example.rezerwacje.data;

import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.HasloForm;
import com.example.rezerwacje.web.forms.ImieNazwiskoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcUzytkownikRepository implements UzytkownikRepository{
    private static final String SELECT_FROM_UZYTKOWNICY_WHERE = "select * from uzytkownicy where ";
    private static final String SELECT_FROM_UZYTKOWNICY_WHERE_NAZWA = SELECT_FROM_UZYTKOWNICY_WHERE + "nazwa = ?";
    private static final String SELECT_FROM_UZYTKOWNICY_WHERE_NAZWA_KIEROWNIKA = SELECT_FROM_UZYTKOWNICY_WHERE + "nazwa_kierownika = ?";
    private static final String INSERT_UZYTKOWNIK = "INSERT INTO UZYTKOWNICY(NAZWA, HASLO, IMIE, NAZWISKO, ROLA, NAZWA_KIEROWNIKA)\n" +
            "VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_UZYTKOWNICY_SET_IMIE_NAZWISKO = "update uzytkownicy set imie = ?, nazwisko = ? where nazwa = ?";
    private static final String UPDATE_UZYTKOWNICY_SET_HASLO = "update uzytkownicy set haslo = ? where nazwa = ?";
    private static final String DELETE_UZYTKOWNICY_WHERE_NAZWA = "delete from uzytkownicy where nazwa = ?";

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

    @Override
    public void zmienImieNazwisko(ImieNazwiskoForm imieNazwiskoForm, String nazwa) {
        jdbcTemplate.update(UPDATE_UZYTKOWNICY_SET_IMIE_NAZWISKO,
                imieNazwiskoForm.getImie(),
                imieNazwiskoForm.getNazwisko(),
                nazwa);
    }

    @Override
    public void zmienHaslo(HasloForm hasloForm, String nazwa) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        jdbcTemplate.update(UPDATE_UZYTKOWNICY_SET_HASLO,
                encoder.encode(hasloForm.getHaslo()),
                nazwa);
    }

    @Override
    public void usunKonto(String nazwa) {
        jdbcTemplate.update(DELETE_UZYTKOWNICY_WHERE_NAZWA,nazwa);
    }
    
    @Override
	public List<Uzytkownik> znajdzPracownika(String nazwaKierownika) {
		return jdbcTemplate.query(SELECT_FROM_UZYTKOWNICY_WHERE_NAZWA_KIEROWNIKA, this::mapRow, nazwaKierownika);
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
