package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import com.example.rezerwacje.web.forms.RezerwacjaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcRezerwacjaRepository implements RezerwacjaRepository {

    private static final String ZNAJDZ_TERMINY_POKOJ = "select DATA_ROZPOCZECIA, DATA_ZAKONCZENIA from rezerwacje where ID_POKOJU = ?";
    private static final String INSERT_REZERWACJA = "insert into rezerwacje (id_pokoju, nazwa_klienta, data_rozpoczecia, data_zakonczenia)" +
            "values(?,?,?,?)";
    private static final String ZNAJDZ_ID_REZERWACJI = "select id from rezerwacje where id_pokoju = ? " +
                                                                "and nazwa_klienta = ?" +
                                                                "and data_rozpoczecia = ?" +
                                                                "and data_zakonczenia = ?";
    private static final String ZNAJDZ_REZERWACJE = "select * from rezerwacje where id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcRezerwacjaRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public List<Rezerwacja> znajdzRezerwacje(Hotel hotel) {
        return null;
    }

    @Override
    public List<Rezerwacja> znajdzRezerwacje(Pokoj pokoj) {
        return null;
    }

    @Override
    public Rezerwacja znajdzRezerwacje(int id) {
        return jdbcTemplate.queryForObject(ZNAJDZ_REZERWACJE,this::mapRow,id);
    }

    private Rezerwacja mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        Hotel hotel = new Hotel();
        Pokoj pokoj = new Pokoj();
        pokoj.setId(resultSet.getInt("ID_POKOJU"));
        Uzytkownik uzytkownik = new Uzytkownik(resultSet.getString("NAZWA_KLIENTA"));
        Date poczatekRezerwacji = resultSet.getDate("DATA_ROZPOCZECIA");
        Date koniecRezerwacji = resultSet.getDate("DATA_ZAKONCZENIA");
        return new Rezerwacja(id,hotel,pokoj,uzytkownik,poczatekRezerwacji,koniecRezerwacji);
    }

    @Override
    public List<Date[]> znajdzTerminyRezerwacji(Pokoj pokoj) {
        return jdbcTemplate.query(ZNAJDZ_TERMINY_POKOJ, this::mapRowDaty, pokoj.getId());
    }

    @Override
    public List<Rezerwacja> znajdzRezerwacjePracownik(Uzytkownik uzytkownik) {
        return null;
    }

    @Override
    public Rezerwacja usunRezerwacje(Rezerwacja rezerwacja) {
        return null;
    }

    @Override
    public void dodajRezerwacje(Rezerwacja rezerwacja) {
        jdbcTemplate.update(
                INSERT_REZERWACJA,
                rezerwacja.getPokoj().getId(),
                rezerwacja.getUzytkownik().getNazwa(),
                rezerwacja.getPoczatekRezerwacji(),
                rezerwacja.getKoniecRezerwacji());
    }

    @Override
    public void modyfikujRezerwacje(Rezerwacja rezerwacja, Uzytkownik uzytkownik) {

    }

    @Override
    public Integer znajdzIdRezerwacji(Rezerwacja rezerwacja) {
        return jdbcTemplate.queryForObject(ZNAJDZ_ID_REZERWACJI,
                ((resultSet, i) -> resultSet.getInt("id")),
                rezerwacja.getPokoj().getId(),
                rezerwacja.getUzytkownik().getNazwa(),
                rezerwacja.getPoczatekRezerwacji(),
                rezerwacja.getKoniecRezerwacji()
                );
    }

    private Date[] mapRowDaty(ResultSet resultSet, int rowNum) throws SQLException {
        Date[] daty = new Date[2];

        daty[0] = resultSet.getDate("DATA_ROZPOCZECIA");
        daty[1] = resultSet.getDate("DATA_ZAKONCZENIA");

        return daty;
    }
}
