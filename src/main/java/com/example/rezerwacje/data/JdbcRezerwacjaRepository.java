package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.rezerwacja.Rezerwacja;
import com.example.rezerwacje.uzytkownik.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcRezerwacjaRepository implements RezerwacjaRepository {
    private static final String SELECT_DATA_ROZPOCZECIA_DATA_ZAKONCZENIA_FROM_REZERWACJE_WHERE_ID_POKOJU =
            "select DATA_ROZPOCZECIA, DATA_ZAKONCZENIA from rezerwacje where ID_POKOJU = ?";
    private static final String INSERT_REZERWACJA =
            "insert into rezerwacje (id_pokoju, nazwa_klienta, data_rozpoczecia, data_zakonczenia)" +
                    "values(?,?,?,?)";
    private static final String ZNAJDZ_ID_REZERWACJI = "select id from rezerwacje where id_pokoju = ? " +
            "and nazwa_klienta = ?" +
            "and data_rozpoczecia = ?" +
            "and data_zakonczenia = ?";
    private static final String SELECT_FROM_REZERWACJE_WHERE_ID = "select * from rezerwacje where id = ?";

    private static final String SELECT_REZERWACJE_FROM_HOTELE = "select * from rezerwacje where id_pokoju in " +
            "(select id from pokoje where id_hotelu = " +
            "(select id from hotele where nazwa_kierownika=?))";
    private static final String DELETE_REZERWACJE = "delete from rezerwacje where id = ?";
    private static final String SELECT_FROM_REZERWACJE_WHERE_NAZWA_KLIENTA = "select * from rezerwacje where NAZWA_KLIENTA = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcRezerwacjaRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public Rezerwacja znajdzRezerwacje(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_REZERWACJE_WHERE_ID, this::mapRow, id);
    }

    @Override
    public List<Rezerwacja> znajdRezerwacje(String nazwaKlienta) {
        return jdbcTemplate.query(SELECT_FROM_REZERWACJE_WHERE_NAZWA_KLIENTA, this::mapRow, nazwaKlienta);
    }

    @Override
    public List<Date[]> znajdzTerminyRezerwacji(Pokoj pokoj) {
        return jdbcTemplate.query(SELECT_DATA_ROZPOCZECIA_DATA_ZAKONCZENIA_FROM_REZERWACJE_WHERE_ID_POKOJU,
                this::mapRowDaty, pokoj.getId());
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
    public Integer znajdzIdRezerwacji(Rezerwacja rezerwacja) {
        return jdbcTemplate.queryForObject(ZNAJDZ_ID_REZERWACJI,
                ((resultSet, i) -> resultSet.getInt("id")),
                rezerwacja.getPokoj().getId(),
                rezerwacja.getUzytkownik().getNazwa(),
                rezerwacja.getPoczatekRezerwacji(),
                rezerwacja.getKoniecRezerwacji()
        );
    }

    @Override
    public List<Rezerwacja> znajdzRezerwacjeHotelu(String nazwaKierownika) {
        return jdbcTemplate.query(SELECT_REZERWACJE_FROM_HOTELE, this::mapRow, nazwaKierownika);
    }

    @Override
    public void usunRezerwacje(int idRezerwacji) {
        jdbcTemplate.update(DELETE_REZERWACJE, idRezerwacji);
    }

    private Date[] mapRowDaty(ResultSet resultSet, int rowNum) throws SQLException {
        Date[] daty = new Date[2];

        daty[0] = resultSet.getDate("DATA_ROZPOCZECIA");
        daty[1] = resultSet.getDate("DATA_ZAKONCZENIA");

        return daty;
    }

    private Rezerwacja mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt("id");
        Hotel hotel = new Hotel();
        Pokoj pokoj = new Pokoj();
        pokoj.setId(resultSet.getInt("ID_POKOJU"));
        Uzytkownik uzytkownik = new Uzytkownik(resultSet.getString("NAZWA_KLIENTA"));
        Date poczatekRezerwacji = resultSet.getDate("DATA_ROZPOCZECIA");
        Date koniecRezerwacji = resultSet.getDate("DATA_ZAKONCZENIA");
        return new Rezerwacja(id, hotel, pokoj, uzytkownik, poczatekRezerwacji, koniecRezerwacji);
    }
}
