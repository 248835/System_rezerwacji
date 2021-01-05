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
    private static final String ZNAJDZ_MIASTO = "select nazwa, miasto, ulica, ocena from hotele where miasto = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcHotelRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public List<Hotel> znajdzHotele(Uzytkownik uzytkownik) {
        return null;
    }

    @Override
    public List<Hotel> znajdzHotele(String miasto) {
        return jdbcTemplate.query(ZNAJDZ_MIASTO, this::mapRow,miasto);
    }

    @Override
    public Hotel znajdzHotelPracownika(Uzytkownik pracownik) {
        return null;
    }

    @Override
    public void addHotel(Uzytkownik kierownik, Hotel hotel) {

    }

    @Override
    public void modyfikujHotel(Hotel hotel) {

    }

    @Override
    public void usunHotel(Hotel hotel) {

    }

    private Hotel mapRow(ResultSet resultSet, int rowNum) throws SQLException{
        String nazwa = resultSet.getString("nazwa");
        String miasto = resultSet.getString("miasto");
        String ulica = resultSet.getString("ulica");
        int ocena = resultSet.getInt("ocena");

        return new Hotel(nazwa,miasto,ulica,ocena);
    }
}
