package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.hotel.Hotel;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class HotelForm {
    @Size(min=2, max=40, message="{nazwa.size}")
    private String nazwa;

    @Size(min=2, max=60, message="{miasto.size}")
    private String miasto;
	
	@Size(min=2, max=60, message="{ulica.size}")
	private String ulica;

	@Min(value = 0, message = "{pokoj.size}")
	private int ocena;

    public HotelForm(Hotel hotel){
        this.nazwa = hotel.getNazwa();
        this.miasto = hotel.getMiasto();
		this.ulica = hotel.getUlica();
		this.ocena = hotel.getOcena();
    }

    public HotelForm() {
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }
	
	public String getUlica() {
		return ulica;
	}
	
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	
	public int getOcena() {
		return ocena;
	}
	
	public void setOcena(int ocena) {
		this.ocena = ocena;
	}
	
	public Hotel toHotel() { return new Hotel(nazwa, miasto, ulica, ocena); }

    @Override
    public String toString() {
        return "HotelForm{" +
                "nazwa='" + nazwa + '\'' +
                ", miasto='" + miasto + '\'' +
				", ulica='" + ulica + '\'' +
				", ocena='" + ocena + '\'' +
                '}';
    }
}
