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
    private static final String INSERT_HOTEL = "INSERT INTO HOTELE(NAZWA,MIASTO,ULICA,OCENA,NAZWA_KIEROWNIKA)\n" +
		"VALUES(?,?,?,?,?)";
	private static final String UPDATE_HOTEL = "UPDATE HOTELE SET NAZWA = ?, MIASTO = ?, ULICA = ?, OCENA = ? WHERE NAZWA_KIEROWNIKA = ?";
	private static final String DELETE_HOTELE_WHERE_ID = "DELETE FROM HOTELE WHERE ID = ?";
	private static final String DELETE_POKOJE_WHERE_ID_HOTELU_WHERE_ID = "DELETE FROM POKOJE WHERE ID = ?";
	
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

    @Override
	public void dodajHotel(Hotel hotel, String nazwaKierownika) {
		jdbcTemplate.update(INSERT_HOTEL,
					hotel.getNazwa(),
					hotel.getMiasto(),
					hotel.getUlica(),
					hotel.getOcena(),
					nazwaKierownika);
	}
	
	@Override
	public void zmienHotel(HotelForm hotelDane, String kierownik) {
		jdbcTemplate.update(UPDATE_HOTEL,
						hotelDane.getNumer(),
						hotelDane.getRodzaj(),
						hotelDane.getRozmiar(),
						hotelDane.getCena(),
						kierownik);
	}
	
	@Override
	public void usunHotel(Hotel hotel) {
		jdbcTemplate.update(DELETE_POKOJE_WHERE_ID_HOTELU, hotel.getID());
		jdbcTemplate.update(DELETE_HOTELE_WHERE_ID, hotel.getID());
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
