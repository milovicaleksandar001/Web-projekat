package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import enums.StatusPorudzbine;

public class Porudzbina {

private int id;
private List<Vozilo> voziloList;
private RentACarObjekat rentACarObjekat;
private LocalDateTime datumIVreme;
private int trajanje;
private int cena;
private Korisnik kupac;
private StatusPorudzbine status;
private int komentarisano;



public Porudzbina() {
	super();
}

public Porudzbina(int id, List<Vozilo> voziloList, RentACarObjekat rentACarObjekat, LocalDateTime datumIVreme,
		int trajanje, int cena, Korisnik kupac, StatusPorudzbine status, int komentarisano) {
	super();
	this.id = id;
	this.voziloList = voziloList;
	this.rentACarObjekat = rentACarObjekat;
	this.datumIVreme = datumIVreme;
	this.trajanje = trajanje;
	this.cena = cena;
	this.kupac = kupac;
	this.status = status;
	this.komentarisano = komentarisano;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public String fileLine() {
	String timestampStr = datumIVreme.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    return Integer.toString(id)+"|"+Integer.toString(rentACarObjekat.getId())+"|"+timestampStr+"|"+Integer.toString(trajanje)+"|"+Integer.toString(cena)+"|"+Integer.toString(kupac.getId())+"|"+status+"|"+Integer.toString(komentarisano)+"\n";
}

public List<Vozilo> getVoziloList() {
	return voziloList;
}

public void setVoziloList(List<Vozilo> voziloList) {
	this.voziloList = voziloList;
}

public RentACarObjekat getRentACarObjekat() {
	return rentACarObjekat;
}

public void setRentACarObjekat(RentACarObjekat rentACarObjekat) {
	this.rentACarObjekat = rentACarObjekat;
}


public LocalDateTime getDatumIVreme() {
	return datumIVreme;
}

public void setDatumIVreme(LocalDateTime datumIVreme) {
	this.datumIVreme = datumIVreme;
}

public int getTrajanje() {
	return trajanje;
}

public void setTrajanje(int trajanje) {
	this.trajanje = trajanje;
}

public int getCena() {
	return cena;
}

public void setCena(int cena) {
	this.cena = cena;
}

public Korisnik getKupac() {
	return kupac;
}

public void setKupac(Korisnik kupac) {
	this.kupac = kupac;
}

public StatusPorudzbine getStatus() {
	return status;
}

public void setStatus(StatusPorudzbine status) {
	this.status = status;
}

public int getKomentarisano() {
	return komentarisano;
}

public void setKomentarisano(int komentarisano) {
	this.komentarisano = komentarisano;
}
	


}
