package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcHotelRepository implements HotelRepository {
    private static final String SELECT_FROM_HOTELE_WHERE = "select * from hotele where ";
    private static final String SELECT_FROM_HOTELE_WHERE_MIASTO = SELECT_FROM_HOTELE_WHERE + "miasto = ?";
    private static final String SELECT_FROM_HOTELE_WHERE_MIASTO_AND_NAZWA = SELECT_FROM_HOTELE_WHERE_MIASTO + "AND nazwa = ?";
    private static final String SELECT_FROM_HOTELE_WHERE_NAZWA_KIEROWNIKA = SELECT_FROM_HOTELE_WHERE + "nazwa_kierownika = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcHotelRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public Hotel znajdzHotelKierownik(String nazwaKierownika) {
        return jdbcTemplate.queryForObject(SELECT_FROM_HOTELE_WHERE_NAZWA_KIEROWNIKA, this::mapRow,nazwaKierownika);
    }

    @Override
    public List<Hotel> znajdzHoteleMiasto(String miasto) {
        return jdbcTemplate.query(SELECT_FROM_HOTELE_WHERE_MIASTO, this::mapRow,miasto);
    }

    @Override
    public Hotel znajdzHotel(String miasto, String nazwa) {
        return jdbcTemplate.queryForObject(SELECT_FROM_HOTELE_WHERE_MIASTO_AND_NAZWA, this::mapRow,miasto,nazwa);
    }

    private Hotel mapRow(ResultSet resultSet, int rowNum) throws SQLException{
        int id = resultSet.getInt("id");
        String nazwa = resultSet.getString("nazwa");
        String miasto = resultSet.getString("miasto");
        String ulica = resultSet.getString("ulica");
        int ocena = resultSet.getInt("ocena");

        return new Hotel(nazwa,miasto,ulica,ocena,id);
    }
}
