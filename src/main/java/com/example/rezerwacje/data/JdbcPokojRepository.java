package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcPokojRepository implements PokojRepository{
    private static final String SELECT_FROM_POKOJE_WHERE_ID_HOTELU = "select * from pokoje where id_hotelu = ?";
    private static final String SELECT_FROM_POKOJE_WHERE_ID = "select * from pokoje where id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPokojRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public List<Pokoj> znajdzPokoje(Hotel hotel) {
        return jdbcTemplate.query(SELECT_FROM_POKOJE_WHERE_ID_HOTELU, this::mapRow,hotel.getId());
    }

    @Override
    public Pokoj znajdzPokoj(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_POKOJE_WHERE_ID,this::mapRow,id);
    }

    @Override
    public Map<Hotel, List<Pokoj>> znajdzOferty(String adres, Date poczatek, Date koniec) {
        return null;
    }

    @Override
    public void dodajPokoj(Pokoj pokoj, Hotel hotel) {

    }

    @Override
    public void usunPokoj(Pokoj pokoj) {

    }

    @Override
    public void modyfikujPokoj(Pokoj pokoj) {

    }

    private Pokoj mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("id");
        int nr = resultSet.getInt("numer");
        int rodzaj = resultSet.getInt("rodzaj");
        int rozmiar = resultSet.getInt("rozmiar");
        int cena = resultSet.getInt("cena");

        return new Pokoj(id,nr,rodzaj,rozmiar,cena);
    }
}
