package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.hotel.Pokoj;

import javax.validation.constraints.Min;

public class PokojForm {
    @Min(value = 0, message = "{pokoj.size}")
    private int numer;
    @Min(value = 0, message = "{pokoj.size}")
    private int rodzaj;
    @Min(value = 1, message = "{pokoj.size}")
	private int rozmiar;
    @Min(value = 0, message = "{pokoj.size}")
	private int cena;
    private int id;

    public PokojForm() {
    }

    public PokojForm(Pokoj pokoj){
        this.id = pokoj.getId();
        this.cena = pokoj.getCena();
        this.numer = pokoj.getNumer();
        this.rodzaj = pokoj.getRodzaj();
        this.rozmiar = pokoj.getRozmiar();
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public int getRodzaj() {
        return rodzaj;
    }

    public void setRodzaj(int rodzaj) {
        this.rodzaj = rodzaj;
    }
	
	public int getRozmiar() {
		return rozmiar;
	}
	
	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
	}
	
	public int getCena() {
		return cena;
	}
	
	public void setCena(int cena) {
		this.cena = cena;
	}
	
	public Pokoj toPokoj() { return new Pokoj(numer, rodzaj, rozmiar, cena); }
	
    @Override
    public String toString() {
        return "HotelForm{" +
                "numer='" + numer + '\'' +
                ", rodzaj='" + rodzaj + '\'' +
				", rozmiar='" + rozmiar + '\'' +
				", cena='" + cena + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }
}
