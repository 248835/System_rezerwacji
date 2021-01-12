package com.example.rezerwacje.data;

import com.example.rezerwacje.hotel.Hotel;
import com.example.rezerwacje.hotel.Pokoj;
import com.example.rezerwacje.web.forms.HotelForm;
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
	private static final String DELETE_HOTELE_WHERE_ID = "DELETE FROM HOTELE WHERE nazwa_kierownika = ?";
	private static final String SELECT_FROM_HOTELE_WHERE_ID = SELECT_FROM_HOTELE_WHERE + "id = ?";
	private static final String SELECT_HOTEL_FROM_POKOJ = SELECT_FROM_HOTELE_WHERE + "id = (select ID_HOTELU from pokoje where id = ?)";
	
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcHotelRepository(JdbcTemplate jdbc) {
        this.jdbcTemplate = jdbc;
    }

    @Override
    public Hotel znajdzHotelKierownik(String nazwaKierownika) {
        Hotel hotel=null;
        try {
            hotel = jdbcTemplate.queryForObject(SELECT_FROM_HOTELE_WHERE_NAZWA_KIEROWNIKA, this::mapRow, nazwaKierownika);
        }catch (Exception e){
            e.printStackTrace();
        }
        return hotel;
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
    public Hotel znajdzHotel(int id) {
        return jdbcTemplate.queryForObject(SELECT_FROM_HOTELE_WHERE_ID,this::mapRow,id);
    }

    @Override
    public Hotel znajdzHotel(Pokoj pokoj) {
        return jdbcTemplate.queryForObject(SELECT_HOTEL_FROM_POKOJ,this::mapRow,pokoj.getId());
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
	public void zmienHotel(HotelForm hotelForm, String kierownik) {
		jdbcTemplate.update(UPDATE_HOTEL,
						hotelForm.getNazwa(),
						hotelForm.getMiasto(),
						hotelForm.getUlica(),
						hotelForm.getOcena(),
						kierownik);
	}
	
	@Override
	public void usunHotel(String nazwaKierownika) {
        jdbcTemplate.update(DELETE_HOTELE_WHERE_ID,nazwaKierownika);
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
