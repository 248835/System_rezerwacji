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
    private static final String ZNAJDZ_HOTEL = "select * from pokoje where id_hotelu = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcPokojRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public List<Pokoj> znajdzPokoje(Hotel hotel) {
        System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee " + hotel.getId());
        List<Pokoj> pokoje = jdbcTemplate.query(ZNAJDZ_HOTEL, this::mapRow,hotel.getId());
        System.out.println();
        for (Pokoj pokoj : pokoje){
            System.out.println(pokoj.getRodzaj());
        }
        return pokoje;
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
