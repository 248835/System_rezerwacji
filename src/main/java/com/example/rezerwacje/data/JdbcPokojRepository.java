package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.web.forms.PokojForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcPokojRepository implements PokojRepository{
    private static final String SELECT_FROM_POKOJE_WHERE = "select * from pokoje where ";
    private static final String SELECT_FROM_POKOJE_WHERE_ID_HOTELU = SELECT_FROM_POKOJE_WHERE + "id_hotelu = ?";
    private static final String SELECT_FROM_POKOJE_WHERE_ID = SELECT_FROM_POKOJE_WHERE + "id = ?";
    private static final String SELECT_FROM_POKOJE_WHERE_ID_HOTELU_FROM_HOTELE =
            "select * from pokoje where id_hotelu = (select id from hotele where nazwa_kierownika = ?)";
	private static final String INSERT_POKOJ = "INSERT INTO POKOJE(NUMER, RODZAJ, ROZMIAR, CENA, ID_HOTELU)\n" +
			"VALUES(?,?,?,?,?)";
	private static final String UPDATE_POKOJ_DANE = "UPDATE POKOJE SET NUMER = ?, RODZAJ = ?, ROZMIAR = ?, CENA = ? WHERE ID = ?";
	private static final String DELETE_POKOJE_WHERE_ID = "DELETE FROM POKOJE WHERE ID = ?";
	private static final String DELETE_REZERWACJE_WHERE_POKOJ = "DELETE FROM REZERWACJE WHERE ID_POKOJU = ?";
    
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
    public List<Pokoj> znajdzPokojeNazwaKierownika(String nazwaKierownika) {
        return jdbcTemplate.query(SELECT_FROM_POKOJE_WHERE_ID_HOTELU_FROM_HOTELE, this::mapRow,nazwaKierownika);
    }

    @Override
    public Pokoj znajdzPokoj(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_POKOJE_WHERE_ID,this::mapRow,id);
    }

    @Override
	public void dodajPokoj(Pokoj pokoj, int id_hotelu) {
		jdbcTemplate.update(INSERT_POKOJ, 
					pokoj.getNumer(),
					pokoj.getRodzaj(),
					pokoj.getRozmiar(),
					pokoj.getCena(),
					id_hotelu);
	}
	
	@Override
	public void zmienDanePokoju(PokojForm pokojDane, int id_pokoju) {
		jdbcTemplate.update(UPDATE_POKOJ_DANE,
						pokojDane.getNumer(),
						pokojDane.getRodzaj(),
						pokojDane.getRozmiar(),
						pokojDane.getCena(),
						id_pokoju);
	}
	
	@Override
	public void usunPokoj(int id) {
		jdbcTemplate.update(DELETE_REZERWACJE_WHERE_POKOJ, id);
		jdbcTemplate.update(DELETE_POKOJE_WHERE_ID, id);
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
