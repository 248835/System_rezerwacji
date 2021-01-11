package com.example.rezerwacje.web.forms;

import com.example.rezerwacje.hotel.Pokoj;

import javax.validation.constraints.Size;

public class PokojForm {
    private int numer;
    private int rodzaj;
	private int rozmiar;
	private int cena;

    public PokojForm(Pokoj pokoj){
        this.numer = pokoj.getNumer();
        this.rodzaj = pokoj.getRodzaj();
		this.rozmiar = pokoj.getRozmiar();
		this.cena = pokoj.getCena();
    }

    public PokojForm() {
    }

    public int getNumer() {
        return numer;
    }

    public void setNumer(int numer) {
        this.numer = numer;
    }

    public String getRodzaj() {
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
}
